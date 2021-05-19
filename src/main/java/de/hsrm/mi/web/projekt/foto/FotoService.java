package de.hsrm.mi.web.projekt.foto;

public interface FotoService {
    public Foto fotoAbspeichern(Foto foto);
    public Optional<Foto> fotoAbfragenNachId(Long id);
    public List<Foto> alleFotosNachZeitstempelSortiert();
    public void loescheFoto(Long id);
}
