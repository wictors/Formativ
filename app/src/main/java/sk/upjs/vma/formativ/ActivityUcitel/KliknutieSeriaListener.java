package sk.upjs.vma.formativ.ActivityUcitel;

import java.util.List;

import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.entity.UspesnostSerie;

public interface KliknutieSeriaListener {
    void klikSeria(Seria seria);
    void novaSeria(Seria seria);
    void uspesnePridanaSeria(Seria vystup);
    void pridajOtazkuDoSerie(Otazka otazka);
    void updateSeria(Seria seria);
    void pridanaOtazka();
    void uspesnosti(List<UspesnostSerie> uspesnostSeries);
}
