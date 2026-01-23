package de.juliseken.scytale.text;

import java.math.BigInteger;

import de.juliseken.scytale.api.Text;

public abstract class AbstractText implements de.juliseken.scytale.api.Text {
    private BigInteger content;

    public AbstractText(BigInteger content) {
        this.content = content;
    }

    public BigInteger getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text other = (Text) o;
        return content.equals(other.getContent());
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
