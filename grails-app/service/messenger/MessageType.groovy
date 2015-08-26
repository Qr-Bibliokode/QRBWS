package messenger

enum MessageType {
    CADASTRO_USUARIO(0, "Cadastro de usuário"),
    DEVOLUCAO(1, "Aviso devolução de livro");

    String description;

    MessageType(int code, String description) {
        this.description = description;
    }
}