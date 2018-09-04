package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

public class UsCardDetailParam extends UsSessionParam {

    @NotBlank(message = "cardId不能为空")
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
