package it.claranet.noleggioAuto.dto;

import java.util.ArrayList;
import java.util.List;

public class AutoDto extends AutoLiteDto {

    private List<NoleggioLiteDto> noleggi = new ArrayList<>();

    public List<NoleggioLiteDto> getNoleggi() {
        return noleggi;
    }

    public void setNoleggi(List<NoleggioLiteDto> noleggi) {
        this.noleggi = noleggi;
    }

}
