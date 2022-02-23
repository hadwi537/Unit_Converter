package Data_Structures.unit_converter;
import java.util.*;

/**
 * Idea here
 * given input 
 * construct a unit_converter
 * e.g read from stdin:
 * 1 tn is 1000 kg
 * 
 * This means we can now convert kgs to tn and vice versa
 * 
 */
public class unit_converterApp {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        unit_converter uc = new unit_converter();
        
        uc.add_information("tn", "kg", 1, 1000);
        uc.add_information("kg", "g", 1, 1000); 
        
        //System.out.println(uc.convert("kg", "g", 2));
        //System.out.println(uc.convert("tn", "g", 2));
        //uc.add_information("tn", "kg", 2, 2000);//TODO can add duplicates
        //System.out.println(uc.convert("tn", "g", 2));
        uc.add_information("s", "m", 1, (double) 1/60); //cannot just enter 1/60
        uc.print_lookup();
        System.out.println(uc.convert("m", "s", 2));
        System.out.println(uc.convert("m", "m", 1));



        

        // while (in.hasNextLine()){
        //     String input = in.nextLine();

        // }
    }
}
