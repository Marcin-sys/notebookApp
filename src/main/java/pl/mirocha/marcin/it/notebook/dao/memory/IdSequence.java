package pl.mirocha.marcin.it.notebook.dao.memory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class IdSequence {
    private int id = 0;

    public int getId() {
        return id++;
    }
}
