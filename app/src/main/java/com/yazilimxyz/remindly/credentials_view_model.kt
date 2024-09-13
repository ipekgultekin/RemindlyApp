package com.yazilimxyz.remindly

object RoleCredentialsRepository {
    var adminEmail: String = ""
    var adminPassword: String = ""

    var yonetimKuruluEmail: String = ""
    var yonetimKuruluPassword: String = ""

    var ekipLideriEmail: String = ""
    var ekipLideriPassword: String = ""

    var asistanEmail: String = ""
    var asistanPassword: String = ""

    var calisanEmail: String = ""
    var calisanPassword: String = ""

    fun loadRoleEmails() {
        getRoleCredentials("admin_credentials", "email") { fetchedEmail ->
            adminEmail = fetchedEmail
        }
        getRoleCredentials("admin_credentials", "password") { fetchedPassword ->
            adminPassword = fetchedPassword
        }
        getRoleCredentials("yonetim_kurulu_credentials", "email") { fetchedEmail ->
            yonetimKuruluEmail = fetchedEmail
        }
        getRoleCredentials("yonetim_kurulu_credentials", "password") { fetchedPassword ->
            yonetimKuruluPassword = fetchedPassword
        }
        getRoleCredentials("ekip_lideri_credentials", "email") { fetchedEmail ->
            ekipLideriEmail = fetchedEmail
        }
        getRoleCredentials("ekip_lideri_credentials", "password") { fetchedPassword ->
            ekipLideriPassword = fetchedPassword
        }
        getRoleCredentials("asistan_credentials", "email") { fetchedEmail ->
            asistanEmail = fetchedEmail
        }
        getRoleCredentials("asistan_credentials", "password") { fetchedPassword ->
            asistanPassword = fetchedPassword
        }
        getRoleCredentials("calisan_credentials", "email") { fetchedEmail ->
            calisanEmail = fetchedEmail
        }
        getRoleCredentials("calisan_credentials", "password") { fetchedPassword ->
            calisanPassword = fetchedPassword
        }
    }
}