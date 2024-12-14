package aqmolaithub.com.traveltec.bot;

import aqmolaithub.com.traveltec.props.BotProperties;
import aqmolaithub.com.traveltec.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TravelAssistantBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final AiAssistantService aiAssistantService;

    @Override
    public String getBotToken() {
        return this.botProperties.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return this.botProperties.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            try {
                SendChatAction sendChatAction = new SendChatAction();
                sendChatAction.setChatId(chatId);
                sendChatAction.setAction(ActionType.TYPING);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            if(message.startsWith("/")) {
                sendMessage(chatId, applyCommand(message));
            } else {
                sendMessage(chatId, this.aiAssistantService.sendMessage(chatId, message));
            }
        }
    }

    private void sendMessage(String chatId, String message) {
        SendMessage sendMessageObject = new SendMessage(chatId, message);
        try {
            execute(sendMessageObject);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String applyCommand(String message) {
        if("/start".equals(message)) {
            return """
                    Привет, путешественник! Добро пожаловать в AqmolaAssistantTravelBot – твоего персонального гида по Акмолинской области. Ты готов исследовать этот невероятно красивый регион?
                    
                    Мы здесь, чтобы помочь тебе:
                    
                        1) Найти лучшие места: От природных чудес, как озеро Боровое, до исторических памятников и музеев.
                        2) Планировать маршруты: Составим для тебя идеальный план путешествия, учитывая твои интересы и время.
                        3) Выбрать жилье: От уютных гостиниц до гостевых домов с местным колоритом.
                        4) Открыть вкусы Акмолы: Рекомендации по ресторанам, где можно попробовать блюда казахской кухни.
                        5) Информация о событиях: Узнай о фестивалях, концертах и мероприятиях, которые нельзя пропустить. 
                    
                    Просто напиши мне, и мы начнем твоё приключение! Есть вопросы или уже знаешь, куда хочешь отправиться? Давай сделаем твой визит в Акмолинскую область незабываемым!""";
        }
        return "";
    }

}
