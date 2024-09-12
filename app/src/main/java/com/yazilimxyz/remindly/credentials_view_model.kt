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
        getRoleCredentials("admin_credentials", "adminEmail") { fetchedEmail ->
            adminEmail = fetchedEmail
        }
        getRoleCredentials("admin_credentials", "adminPassword") { fetchedEmail ->
            adminPassword = fetchedEmail
        }
        getRoleCredentials("yonetim_kurulu_credentials", "yonetimKuruluEmail") { fetchedEmail ->
            yonetimKuruluEmail = fetchedEmail
        }
        getRoleCredentials("yonetim_kurulu_credentials", "yonetimKuruluPassword") { fetchedEmail ->
            yonetimKuruluPassword = fetchedEmail
        }
        getRoleCredentials("ekip_lideri_credentials", "ekipLideriEmail") { fetchedEmail ->
            ekipLideriEmail = fetchedEmail
        }
        getRoleCredentials("ekip_lideri_credentials", "ekipLideriPassword") { fetchedEmail ->
            ekipLideriPassword = fetchedEmail
        }
        getRoleCredentials("asistan_credentials", "asistanEmail") { fetchedEmail ->
            asistanEmail = fetchedEmail
        }
        getRoleCredentials("asistan_credentials", "asistanPassword") { fetchedEmail ->
            asistanPassword = fetchedEmail
        }
        getRoleCredentials("calisan_credentials", "calisanEmail") { fetchedEmail ->
            calisanEmail = fetchedEmail
        }
        getRoleCredentials("calisan_credentials", "calisanPassword") { fetchedEmail ->
            calisanPassword = fetchedEmail
        }
    }
}