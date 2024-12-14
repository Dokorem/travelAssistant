package aqmolaithub.com.traveltec.client;

public interface OpenAiClient {

    String createThread();

    void createMessage(String threadId, String messageContent);

    String createRun(String threadId);

    String retrieveRun(String threadId, String runId);

}
