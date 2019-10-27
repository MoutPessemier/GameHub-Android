package digitized.gamehub.domain

import java.util.*

class User {

    private var firstName: String
    private var lastName: String
    private var telephone: String
    private var email: String
    private var birthDate: Date
    private var userRole: UserRoles

    constructor(
        firstName: String,
        lastName: String,
        telephone: String,
        email: String,
        birthDate: Date,
        userRole: UserRoles
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.telephone = telephone
        this.email = email
        this.birthDate = birthDate
        this.userRole = userRole
    }
}