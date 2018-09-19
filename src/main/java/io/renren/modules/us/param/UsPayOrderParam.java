package io.renren.modules.us.param;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class UsPayOrderParam extends UsSessionParam {

    @NotBlank(message = "cardId不能为空")
    private String cardId;

    @NotBlank(message = "subject不能为空")
    private String subject;

    @NotBlank(message = "body不能为空")
    private String body;

    @NotBlank(message = "channel不能为空")
    private String channel;

    @DecimalMin(value = "0.01")
    private Double amount;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
