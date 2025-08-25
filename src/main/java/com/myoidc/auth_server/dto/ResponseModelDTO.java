package com.myoidc.auth_server.dto;

import com.myoidc.auth_server.models.enums.OptionLabel;

public class ResponseModelDTO {
    @com.fasterxml.jackson.annotation.JsonProperty("option_id")
    private OptionLabel option;
    private boolean checked;

    public ResponseModelDTO(OptionLabel option, boolean checked) {
        this.option = option;
        this.checked = checked;
    }

    public OptionLabel getOption() {
        return option;
    }

    public void setOption(OptionLabel option) {
        this.option = option;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
