
package exchange2013.ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfUserIdType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUserIdType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserId" type="{http://schemas.microsoft.com/exchange/services/2006/types}UserIdType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUserIdType", propOrder = {
    "userId"
})
public class ArrayOfUserIdType {

    @XmlElement(name = "UserId", required = true)
    protected List<UserIdType> userId;

    /**
     * Gets the value of the userId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserIdType }
     * 
     * 
     */
    public List<UserIdType> getUserId() {
        if (userId == null) {
            userId = new ArrayList<UserIdType>();
        }
        return this.userId;
    }

}