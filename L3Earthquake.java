
public class L3Earthquake {

    public static String classifyPrint(double richterScale) {
        return switch ((int) richterScale) {
            case 0, 1 ->
                "Micro";
            case 2, 3 ->
                "Minor";
            case 4 ->
                "Light";
            case 5 ->
                "Moderate";
            case 6 ->
                "Strong";
            case 7 ->
                "Major";
            default ->
                "Great";
        };
    }

    public static void main(String[] args) {
        System.out.println(classifyPrint(3.7));
    }
}
