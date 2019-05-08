package sk.upjs.vma.formativ.ActivityStudent;


import java.util.List;

import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Seria;

public interface KliknutieSeriaStudentListener {
    void klikSeria(Seria seria);
    void pridajSeriu(String cislo);
    void uspesnePridanaSeria(Boolean aBoolean);
    void odpovedam(Odpoved odpoved);
    void zpracujOtazky(List<Otazka> otazkas);
}
