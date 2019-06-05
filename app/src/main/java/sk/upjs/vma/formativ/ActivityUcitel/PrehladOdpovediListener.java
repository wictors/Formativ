package sk.upjs.vma.formativ.ActivityUcitel;


import java.util.List;

import sk.upjs.vma.formativ.entity.Odpoved;

public interface PrehladOdpovediListener {
    void spracujOdpovede(List<Odpoved> odpoveds);
}
