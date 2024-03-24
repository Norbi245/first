package commands;

import database.Insert;
import database.Select;
import objects.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartCommand extends Bot {
    public StartCommand(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            if (command.equals("/start")) {
                handleStart(update);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleStart(Update update) {
        if (!Select.isUser(update.getMessage().getFrom().getId().toString())) {
            Insert.user(update.getMessage().getFrom().getId().toString());
        }

        sendMainKeyboard(update.getMessage().getChatId());
    }

    public void sendMainKeyboard(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        String responseText = "Witaj!";
        message.setText(responseText);
        message.setParseMode("MarkdownV2");

        InlineKeyboardButton buttonShop = new InlineKeyboardButton();
        buttonShop.setText("\uD83C\uDFEA Sklep");
        buttonShop.setCallbackData("shop_callback");

        InlineKeyboardButton buttonWallet = new InlineKeyboardButton();
        buttonWallet.setText("\uD83D\uDCB6 Portfel");
        buttonWallet.setCallbackData("wallet_callback");

        InlineKeyboardButton buttonCart = new InlineKeyboardButton();
        buttonCart.setText("\uD83D\uDED2 Nie czekaj!!");
        buttonCart.setCallbackData("cart_callback");

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(buttonShop);
        rowInline.add(buttonWallet);
        rowInline.add(buttonCart);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);

        message.setReplyMarkup(markupInline);

        executeSendMessage(message);
    }
    //zastosowanie wyslania nowej wiadomosci 
    private void handleCallbackQuery(Update update) {
        String callData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        if ("first_page".equals(callData)) {
            sendMainKeyboard(chatId);
        }
    }

    private void executeSendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}