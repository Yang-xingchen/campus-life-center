package campuslifecenter.usercenter.entry;

import java.io.Serializable;

public class AccountOrganization extends AccountOrganizationKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_organization.role
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    private Byte role;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account_organization
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_organization.role
     *
     * @return the value of account_organization.role
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    public Byte getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_organization
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    public AccountOrganization withRole(Byte role) {
        this.setRole(role);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_organization.role
     *
     * @param role the value for account_organization.role
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    public void setRole(Byte role) {
        this.role = role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_organization
     *
     * @mbg.generated Wed Nov 25 15:44:53 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", role=").append(role);
        sb.append("]");
        return sb.toString();
    }
}