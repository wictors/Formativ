package sk.upjs.vma.formativ.ActivityUcitel;

import java.util.ArrayList;
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
    void updateOtazka(Otazka otazka);
    void odstranOtazku(Otazka editOtazka);
    void vymazSerie(ArrayList<Seria> zoznamOznac);
    void serieZmazane();
    void ukazOdpovede(Otazka otazka);
}
