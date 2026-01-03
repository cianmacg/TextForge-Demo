package ie.atu.cloudnative.TextForge_Demo.request;

public class TextRequest {
    private String content;
    private String action;

    public TextRequest(String content, String action) {
        this.content = content;
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
