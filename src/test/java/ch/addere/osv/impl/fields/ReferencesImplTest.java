package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.ReferencesImpl.REFERENCES_KEY;
import static ch.addere.osv.impl.fields.ReferencesImpl.of;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.References;
import ch.addere.osv.fields.references.ReferenceType;
import ch.addere.osv.fields.references.ReferenceUrl;
import ch.addere.osv.impl.fields.references.ReferenceTypeImpl;
import ch.addere.osv.impl.fields.references.ReferenceUrlImpl;
import java.net.URI;
import org.junit.jupiter.api.Test;

class ReferencesImplTest {

  private static final String VALID_URL = "https://osv.dev/";

  @Test
  void testJsonKey() {
    assertThat(REFERENCES_KEY).isEqualTo("references");
  }

  @Test
  void testValidReference() {
    References references = of(type(), url());
    assertThat(references).satisfies(ref -> {
      assertThat(ref.referenceType()).isEqualTo(ReferenceTypeImpl.ARTICLE);
      assertThat(ref.referenceUrl().value()).isEqualTo(VALID_URL);
    });
  }

  @Test
  void testSameness() {
    References references = of(type(), url());
    References otherReferences = references;
    assertThat(references).isEqualTo(otherReferences);
  }

  @Test
  void testEquality() {
    References references = of(type(), url());
    References otherReferences = of(type(), url());
    assertThat(references).isEqualTo(otherReferences);
  }

  @Test
  void testNonEquality() {
    References references = of(type(), url());
    References otherReferences = of(ReferenceTypeImpl.FIX, url());
    assertThat(references).isNotEqualTo(otherReferences);
  }

  @Test
  void testHashCode() {
    References references = of(type(), url());
    References otherReferences = of(type(), url());
    assertThat(references.hashCode()).isEqualTo(otherReferences.hashCode());
  }

  @Test
  void testToString() {
    References references = of(type(), url());
    assertThat(references.toString()).isEqualTo(REFERENCES_KEY + ": " + join(", ",
        typeToString(),
        urlToString()));
  }

  private static ReferenceType type() {
    return ReferenceTypeImpl.ARTICLE;
  }

  private static ReferenceUrl url() {
    return ReferenceUrlImpl.of(URI.create(VALID_URL));
  }

  private static String typeToString() {
    return "type: ARTICLE";
  }

  private static String urlToString() {
    return "url: " + VALID_URL;
  }
}