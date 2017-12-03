public enum Item {
    SPRINKLES, FILLING , SHELL;

    @Override
    public String toString() {
        switch(this) {
            case SPRINKLES: return "sprinkles";
            case FILLING: return "filling";
            case SHELL: return "shell";
            default: throw new IllegalArgumentException();
        }
    }
}
