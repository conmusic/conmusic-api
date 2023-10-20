package school.sptech.conmusicapi.modules.media.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.media.entities.Media;
import school.sptech.conmusicapi.modules.media.mapper.MediaMapper;
import school.sptech.conmusicapi.modules.media.repositories.IMediaRepository;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private IMediaRepository mediaRepository;

    public String uploadFileArtist(MultipartFile file, User user) throws IOException {
        int count = mediaRepository.countByUserId(user.getId());

        String fileName = user.getId() + "-" + user.getName() + "-" + count;
        File convertedFile = convertMultiPartToFile(file);
        amazonS3Client.putObject(new PutObjectRequest(bucketName,
                fileName,
                convertedFile));
        convertedFile.delete();

        saveFileArtist(user, file.getContentType(), fileName);

        return fileName;
    }

    public String uploadFileEstablishment(MultipartFile file, Establishment establishment) throws IOException {
        int count = mediaRepository.countByEstablishmentId(establishment.getId());

        String fileName = establishment.getId() + "-" + establishment.getFantasyName() + "-" + count;
        File convertedFile = convertMultiPartToFile(file);
        amazonS3Client.putObject(new PutObjectRequest(bucketName,
                fileName,
                convertedFile));
        convertedFile.delete();

        saveFileEstablishment(establishment, file.getContentType(), fileName);

        return fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object object = amazonS3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = object.getObjectContent();

        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucketName, fileName);
        return String.format("File deleted: %s", fileName);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new IOException("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private void saveFileArtist(User user, String type, String url) {
        Media media = MediaMapper.mapArtist(user, type, url);
        mediaRepository.save(media);
    }

    private void saveFileEstablishment(Establishment establishment, String type, String url) {
        Media media = MediaMapper.mapEstablishment(establishment, type, url);
        mediaRepository.save(media);
    }
}
