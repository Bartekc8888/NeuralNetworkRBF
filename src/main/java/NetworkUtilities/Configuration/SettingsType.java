package NetworkUtilities.Configuration;

public enum SettingsType {
    Type1("Zadanie 1"),
    Type2("Zadanie 2"),
    Type3a("Zadanie 3a"),
    Type3b("Zadanie 3b");
    
    private String description;
    
    private SettingsType(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
