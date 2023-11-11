package school.sptech.conmusicapi.modules.avaliation.util;

public class RoundAvaliation {
    public static Double round(Double value){
        if (value == 0){
            return 0.0;
        } else if (value >0 && value<=0.5){
            return 0.5;
        } else if (value >0.5 && value<= 1.0){
            return 1.0;
        } else if (value > 1 && value <= 1.5){
            return 1.5;
        } else if (value > 1.5 && value <=2.0) {
            return 2.0;
        } else if (value > 2.0 && value <=2.5) {
            return 2.5;
        } else if (value > 2.5 && value <=3.0) {
            return 3.0;
        } else if (value > 3.0 && value <=3.5) {
            return 3.5;
        } else if (value > 3.5 && value <=4.0) {
            return 4.0;
        } else if (value > 4.0 && value <=4.5) {
            return 4.5;
        } else{
            return 5.0;
        }
    }
}
