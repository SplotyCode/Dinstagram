package team.gutterteam123;

import lombok.Getter;

public class Master {

    @Getter private static Master instance;

    public static void main(String[] args) {
        instance = new Master();
    }

    private Master() {
        System.out.println("Master Running!");
    }

}
