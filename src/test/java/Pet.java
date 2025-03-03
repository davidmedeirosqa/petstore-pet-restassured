public class Pet {
    // Definindo uma classe Pet para guardar a estrutura de dados sobre os animais
    public int id;

    public class Category {
        public int id;
        public String name;
    }

    public String name;
    public String[] photoUrls;

    // Definindo a tag
    public class Tag {
        public int id;
        public String name;
    }

    public Tag tags[]; // Var de uma lista de tags

    public String status;
}

// Campos compostos, criar sub classes
