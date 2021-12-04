package ch.addere.osv.impl.fields;

import static java.lang.String.join;

import ch.addere.osv.fields.References;
import ch.addere.osv.fields.references.ReferenceType;
import ch.addere.osv.fields.references.ReferenceUrl;
import java.util.Objects;

/**
 * Reference field property.
 */
public final class ReferencesImpl implements References {

  public static final String REFERENCES_KEY = "references";

  private final ReferenceType referenceType;
  private final ReferenceUrl referenceUrl;

  private ReferencesImpl(ReferenceType referenceType,
      ReferenceUrl referenceUrl) {
    this.referenceType = referenceType;
    this.referenceUrl = referenceUrl;
  }

  public static References of(ReferenceType referenceType, ReferenceUrl referenceUrl) {
    return new ReferencesImpl(referenceType, referenceUrl);
  }

  @Override
  public ReferenceType referenceType() {
    return referenceType;
  }

  @Override
  public ReferenceUrl referenceUrl() {
    return referenceUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferencesImpl that = (ReferencesImpl) o;
    return Objects.equals(referenceType, that.referenceType) && Objects.equals(
        referenceUrl, that.referenceUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referenceType, referenceUrl);
  }

  @Override
  public String toString() {
    return REFERENCES_KEY + ": " + join(", ", referenceType.toString(), referenceUrl.toString());
  }
}