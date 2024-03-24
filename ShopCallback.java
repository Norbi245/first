package callbacks;

import database.Select;
import objects.Bot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ShopCallback extends Bot {
    private String callback_name = "shop_callback";
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
    List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();

    public ShopCallback(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callData = callbackQuery.getData();

            if (callback_name.equals(callData)) {
                Message message = (Message) callbackQuery.getMessage();
                Chat chat = message.getChat();

                InlineKeyboardButton button = new InlineKeyboardButton(), button2 = new InlineKeyboardButton(), backButton = new InlineKeyboardButton();
                button.setText("Promocje Sklep1 \uD83C\uDDF5\uD83C\uDDF1");
                button.setCallbackData("shop_list_1");
                rowInline.add(button);

                button2.setText("Promocje Sklep2 \uD83C\uDDF5\uD83C\uDDF1");
                button2.setCallbackData("shop_list_2");
                rowInline.add(button2);

                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);

                backButton.setText("\u2B05 Powrót");
                backButton.setCallbackData("first_page");

                List<InlineKeyboardButton> backRow = new ArrayList<>();
                backRow.add(backButton);

                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(markupInline.getKeyboard());
                rowsInline.add(backRow);

                markupInline.setKeyboard(rowsInline);

                EditMessageText newMessage = new EditMessageText();
                newMessage.setChatId(chat.getId());
                newMessage.setMessageId(message.getMessageId());

                // :
                //System.out.println("Wiadomosc:");
                //System.out.println("Chat ID: " + chat.getId());
                //System.out.println("Message ID: " + message.getMessageId());

                newMessage.setText("*Wybierz kategorie które chcesz przeglądać*");
                newMessage.setParseMode("MarkdownV2");
                newMessage.setReplyMarkup(markupInline);

                try {
                    execute(newMessage);
                } catch (TelegramApiException e) {
                    e.getStackTrace();
                }

            }
        }
    }
}
