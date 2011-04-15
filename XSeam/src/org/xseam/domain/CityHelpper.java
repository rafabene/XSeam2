package org.xseam.domain;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Classe utilitária para fazer o Unmarshalling dos XMLs de Município além de
 * outras atividades repetitivas
 * 
 * @author rafael
 */
public class CityHelpper {

    private static Unmarshaller unmarshaller;
    private static Map<State, List<City>> cache = new HashMap<State, List<City>>();

    static {
        JAXBContext jaxbctx;
        try {
            jaxbctx = JAXBContext.newInstance(ItemCity.class, City.class, CityObjectFactory.class);
            unmarshaller = jaxbctx.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<City> getCities(State state) {
        List<City> retorno = cache.get(state);
        if (retorno == null || retorno.size() == 0) {
            String path = "/domain/city/" + state.getDescription() + ".xml";
            InputStream is = CityHelpper.class.getResourceAsStream(path);
            JAXBElement<ItemCity> items;
            try {
                items = (JAXBElement<ItemCity>) unmarshaller.unmarshal(is);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            ItemCity item = items.getValue();
            retorno = item.getDominio();
            cache.put(state, retorno);
        }
        return retorno;
    }

    public static City getCity(int id) {
        String scodUf = String.valueOf(id).substring(0, 2);
        State uf = StateHelpper.getState(scodUf);
        List<City> cities = getCities(uf);
        for (City city : cities) {
            if (city.getId().equals(id)) {
                return city;
            }
        }
        throw new NoSuchElementException("There isn't a City with this IBGE code: " + id);
    }

}
