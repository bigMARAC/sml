public enum Commands {
    READ(10),
    WRITE(11),
    LOAD(20),
    STORE(21),
    ADD(30),
    SUBTRACT(31),
    DIVIDE(32),
    MULTIPLY(33),
    BRANCH(40),
    BRANCHNEG(41),
    BRANCHZERO(42),
    HALT(43);

    
    private final Integer code;

    private Commands(Integer code) {
        this.code = code;
    }
    
    public Integer getCode() {
        return this.code;
    }
}