package dmdweb.gen.page.guiservice;

import java.io.Serializable;

public interface ModalResultCallback<E> extends Serializable {

    public static class DoNothingCallback implements ModalResultCallback<Object> {

        private static final long serialVersionUID = 1L;

        @Override
        public void onResult(Object result) {
            // do nothing
        }
    }

    void onResult(E result);

}
