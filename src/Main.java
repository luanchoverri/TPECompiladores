public class Main {
    public static void main(String[] args) {
        String buffer = "1.17549435F-38";
        if (buffer.contains("F") && !buffer.endsWith("F")){
            buffer = buffer.replace("F","e");
            buffer += "f";
        }
        try {
            float floatBuffer = Float.parseFloat(buffer);
            if ((!(1.17549435e-38 < floatBuffer) || !(floatBuffer < 3.40282347e+38) || !(-3.40282347e+38 < floatBuffer) || !(floatBuffer < -1.17549435e-38))){
                throw new Exception("out of range");
            }
        } catch (Exception e){
            e.printStackTrace(); //fuera de rango
        }
    }
}