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
    
    public void run() {
        if (load()) {
            execute();
        }
        getMemory();
        input.close();
    }
    
    private Boolean load() {
        Integer count = 0;
        Integer word = 0;
        
        do {
            System.out.printf("%02d ? ", count);
            word = input.nextInt();
            if ((word >= -9999 && word <= +9999) || word.equals(-99999)) {
                memory.add(count++, word);
            }
        } while (!word.equals(-99999) && count < 100);
        
        System.out.println("*** Program loading completed ***");
        System.out.println("*** Program execution begins ***");
        return count == 1 ? false : true;
    }
    
    private void execute() {
        Boolean stop = false;
        
        while (!stop) {
            instructionRegister = memory.get(instructionCounter);
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;
            stop = operate();
            instructionCounter++;
        }
    }
    
    private Boolean operate() {
        switch(operationCode) {
            case 10: // READ
                System.out.println("Enter an integer: ");
                memory.add(operand, input.nextInt());
                memory.remove(operand + 1);
                break;
            case 11: // WRITE
                System.out.println(memory.get(operand));
                break;
            case 20: // LOAD
                accumulator = memory.get(operand);
                break;
            case 21: // STORE
                memory.add(operand, accumulator);
                memory.remove(operand + 1);
                break;
            case 30: // ADD
                accumulator += memory.get(operand);
                checkIsValid(accumulator);
                break;
            case 31: // SUBTRACT
                accumulator -= memory.get(operand);
                checkIsValid(accumulator);
                break;
            case 32: // DIVIDE
                if (memory.get(operand) == 0) {
                    System.out.println("*** Attempt to divide by zero ***");
                    System.out.println("*** Simpletron execution abnormally terminated ***");
                    getMemory();
                    System.exit(1);
                    return false;
                }
                accumulator /= memory.get(operand);
                checkIsValid(accumulator);
                break;
            case 33: // MULTIPLY
                accumulator *= memory.get(operand);
                checkIsValid(accumulator);
                break;
            case 40: // BRANCH
                instructionCounter = operand - 1;
                break;
            case 41: // BRANCHNEG
                instructionCounter = accumulator < 0 ? operand - 1 : instructionCounter;
                break;
            case 42: // BRANCHZERO
                instructionCounter = accumulator == 0 ? operand - 1 : instructionCounter;
                break;
            case 43: // HALT
                System.out.println("*** Simpletron execution terminated ***");
                return true;
            default:
                System.out.println("*** Invalid Operation Code ***");
                System.exit(1);
        }
        
        return false;
    }
    
    private Boolean checkIsValid(Integer number) {
        if (number >= -9999 && number <= +9999) {
            return true;
        }
        
        System.out.println("\n*** Invalid Arithmetic Operation ***");
        System.out.println("*** Simpletron execution abnormally terminated ***");
        getMemory();
        System.exit(1);
        return false;
    }
    
    private void getMemory() {
        System.out.printf("\nREGISTERS:");
        System.out.printf("accumulator %d\n", accumulator);
        System.out.printf("instructionCounter %02d\n", instructionCounter);
        System.out.printf("instructionRegister +%02d\n", instructionRegister);
        System.out.printf("operationCode %02d\n", operationCode);
        System.out.printf("operand %02d\n", operand);
        System.out.println("MEMORY: ");
        System.out.println(memory);
    }
}