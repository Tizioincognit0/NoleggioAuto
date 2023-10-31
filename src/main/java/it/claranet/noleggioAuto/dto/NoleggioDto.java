package it.claranet.noleggioAuto.dto;

public class NoleggioDto extends NoleggioLiteDto {

    private AutoLiteDto auto;

    public AutoLiteDto getAuto() {
        return auto;
    }

    public void setAuto(AutoLiteDto auto) {
        this.auto = auto;
    }

}
