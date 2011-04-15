package org.xseam.domain;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class StateHelpper {

    private static final Unmarshaller unmarshaller;
    private static List<State> ufs;

    static {
        JAXBContext jaxbctx;
        try {
            jaxbctx = JAXBContext.newInstance(ItemState.class, State.class, StateObjectFactory.class);
            unmarshaller = jaxbctx.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<State> getStates() {
        if (ufs == null) {
            InputStream is = StateHelpper.class.getResourceAsStream("/domain/state/brazil.xml");
            JAXBElement<ItemState> items;
            try {
                items = (JAXBElement<ItemState>) unmarshaller.unmarshal(is);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            ItemState item = items.getValue();
            ufs = item.getDominio();
        }
        return ufs;
    }

    public static State getState(int codigo) {
        if (codigo == 0) {
            return null;
        }

        List<State> ufs = getStates();
        for (State uf : ufs) {
            if (uf.getId().equals(codigo)) {
                return uf;
            }
        }
        throw new NoSuchElementException("There isn't a State with this IBGE code: " + codigo);
    }

    /**
     * Return the State based on its code or by its value if code isn't find.
     * 
     * @param value
     *            State code or value
     * @return the State
     */
    public static State getState(String value) {
        try {
            int codigo = Integer.parseInt(value);
            return getState(codigo);
        } catch (NumberFormatException e) {
            // Do nothing. Will try to get State by its value
        }
        return getStateByDescription(value);
    }

    public static State getStateByDescription(String description) {
        List<State> ufs = getStates();
        for (State uf : ufs) {
            if (uf.getDescription().equals(description)) {
                return uf;
            }
        }
        throw new NoSuchElementException("There isn't a State with this description: '" + description + "'");
    }

}
