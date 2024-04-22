import java.util.ArrayList;
import java.util.Scanner;

public class Simpletron {
    private ArrayList<Integer> memory = new ArrayList<Integer>();
    private Integer  operationCode, operand, instructionCounter, instructionRegister, accumulator;
    private Scanner input = new Scanner(System.in);
    
    public Simpletron() {
        System.out.println("*** Welcome to Simpletron! ***"
        + "\n*** Please enter your program one instruction ***"
        + "\n*** (or data word) at a time. I will display ***"
        + "\n*** the location number and a question mark (?). ***"
        + "\n*** You then type the word for that location. ***"
        + "\n*** Type -99999 to stop entering your program. ***");
        
        operationCode = 0;
        operand = 0;
        instructionCounter = 0;
        instructionRegister = 0;
        accumulator = 0;
    }
    
    public void load() {
        Integer count = 0;
        Integer word = 0;
        
        do {
            System.out.printf("%02d ? ", count);
            word = input.nextInt();
            memory.add(count++, word);
        } while (!word.equals(-99999) && count < 100);
        
        System.out.println("*** Program loading completed ***\n*** Program execution begins ***");
    }
    
    public void run() {
        load();
        execute();
        getMemory();
        input.close();
    }
    
    private boolean execute() {
        Boolean stop = false;
        
        while(!stop) {
            instructionRegister = memory.get(instructionCounter);
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;
            try {
                stop = operate();
                instructionCounter++;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        return true;
    }
    
    private Boolean operate() throws Exception {
        switch (operationCode) {
            case 10:
                System.out.println("Enter an integer: ");
                memory.add(operand, input.nextInt());
                memory.remove(operand + 1);
                break;
                // return "READ";
            case 11:
                System.out.println(memory.get(operand));
                break;
                // return "WRITE";
            case 20:
                accumulator = memory.get(operand);
                break;
                // return "LOAD";
            case 21:
                memory.add(operand, accumulator);
                break;
                // return "STORE";
            case 30:
                accumulator += memory.get(operand);
                break;
                // return "ADD";
            case 31:
                accumulator -= memory.get(operand);
                break;
                // return "SUBTRACT";
            case 32:
                accumulator /= memory.get(operand);
                break;
                // return "DIVIDE";
            case 33:
                accumulator *= memory.get(operand);
                break;
                // return "MULTIPLY";
            case 40:
                instructionCounter = operand - 1;
                break;
                // return "BRANCH";
            case 41:
                instructionCounter = accumulator < 0 ? operand - 1 : instructionCounter;
                break;
                // return "BRANCHNEG";
            case 42:
                instructionCounter = accumulator == 0 ? operand - 1 : instructionCounter;
                break;
                // return "BRANCHZERO";
            case 43:
                System.out.println("*** Simpletron execution terminated ***");
                return true;
                // return "HALT";
            default:
                throw new Exception("Morreu");
        }
        
        return false;
    }
    
    public ArrayList<Integer> getMemory() {
        System.out.println("REGISTERS:");
        System.out.printf("accumulator: %d\n", accumulator);
        System.out.printf("instructionCounter: %02d\n", instructionCounter);
        System.out.printf("instructionRegister: +%d\n", instructionRegister);
        System.out.printf("operationCode: %02d\n", operationCode);
        System.out.printf("operand: %02d\n", operand);
        System.out.println("MEMORY:");
        System.out.println(this.memory);
        return this.memory;
    }
}
