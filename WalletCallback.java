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

public class WalletCallback extends Bot {
    private String callback_name = "wallet_callback";
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
    List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();
    public WalletCallback(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callData = callbackQuery.getData();

            if(callback_name.equals(callData)) {
                Message message = (Message) callbackQuery.getMessage();
                Chat chat = message.getChat();

                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText("\uD83D\uDCB0 Doładuj portfel");
                button.setCallbackData("payment_init_value");
                rowInline.add(button);
                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);

               EditMessageText newMessage = new EditMessageText();
                newMessage.setChatId(chat.getId());
                newMessage.setMessageId(message.getMessageId());
                newMessage.setText("*Portfel\nSaldo: `" + Select.userBalance(update.getCallbackQuery().getFrom().getId().toString()) + "` PLN*");
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
