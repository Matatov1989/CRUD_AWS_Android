package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the MyFirstTest type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MyFirstTests", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class MyFirstTest implements Model {
  public static final QueryField ID = field("MyFirstTest", "id");
  public static final QueryField NAME = field("MyFirstTest", "name");
  public static final QueryField AGE = field("MyFirstTest", "age");
  public static final QueryField COUNTRY = field("MyFirstTest", "country");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int", isRequired = true) Integer age;
  private final @ModelField(targetType="String", isRequired = true) String country;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public String getCountry() {
      return country;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private MyFirstTest(String id, String name, Integer age, String country) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.country = country;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MyFirstTest myFirstTest = (MyFirstTest) obj;
      return ObjectsCompat.equals(getId(), myFirstTest.getId()) &&
              ObjectsCompat.equals(getName(), myFirstTest.getName()) &&
              ObjectsCompat.equals(getAge(), myFirstTest.getAge()) &&
              ObjectsCompat.equals(getCountry(), myFirstTest.getCountry()) &&
              ObjectsCompat.equals(getCreatedAt(), myFirstTest.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), myFirstTest.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getAge())
      .append(getCountry())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MyFirstTest {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("country=" + String.valueOf(getCountry()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static MyFirstTest justId(String id) {
    return new MyFirstTest(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      age,
      country);
  }
  public interface NameStep {
    AgeStep name(String name);
  }
  

  public interface AgeStep {
    CountryStep age(Integer age);
  }
  

  public interface CountryStep {
    BuildStep country(String country);
  }
  

  public interface BuildStep {
    MyFirstTest build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, AgeStep, CountryStep, BuildStep {
    private String id;
    private String name;
    private Integer age;
    private String country;
    @Override
     public MyFirstTest build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MyFirstTest(
          id,
          name,
          age,
          country);
    }
    
    @Override
     public AgeStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CountryStep age(Integer age) {
        Objects.requireNonNull(age);
        this.age = age;
        return this;
    }
    
    @Override
     public BuildStep country(String country) {
        Objects.requireNonNull(country);
        this.country = country;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer age, String country) {
      super.id(id);
      super.name(name)
        .age(age)
        .country(country);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder country(String country) {
      return (CopyOfBuilder) super.country(country);
    }
  }
  
}
