package com.example.e_library.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_library.ui.theme.screens.about.AboutScreenAdmin
import com.example.e_library.ui.theme.screens.about.AboutScreenClient
import com.example.e_library.ui.theme.screens.about.AboutScreenDeliveryPersonnel
import com.example.e_library.ui.theme.screens.about.AboutScreenGuest
import com.example.e_library.ui.theme.screens.about.AboutScreenStaff
import com.example.e_library.ui.theme.screens.admin.AdminClientEdit
import com.example.e_library.ui.theme.screens.admin.AdminDeliveryPersonnelEdit
import com.example.e_library.ui.theme.screens.admin.AdminEditAccount
import com.example.e_library.ui.theme.screens.admin.AdminEditHome
import com.example.e_library.ui.theme.screens.admin.AdminLogInScreen
import com.example.e_library.ui.theme.screens.admin.AdminRegisterScreen
import com.example.e_library.ui.theme.screens.admin.AdminStaffEdit
import com.example.e_library.ui.theme.screens.admin.AdminViewAccount
import com.example.e_library.ui.theme.screens.books.AddBooksScreen
import com.example.e_library.ui.theme.screens.books.BooksHomeScreen
import com.example.e_library.ui.theme.screens.books.EditBooksScreen
import com.example.e_library.ui.theme.screens.books.ViewAllBooksScreen
import com.example.e_library.ui.theme.screens.books.ViewBooksScreen
import com.example.e_library.ui.theme.screens.borrowing.BorrowBooksClientScreen
import com.example.e_library.ui.theme.screens.borrowing.BorrowBooksStaffScreen
import com.example.e_library.ui.theme.screens.borrowing.BorrowHomeScreen
import com.example.e_library.ui.theme.screens.borrowing.ViewClientBorrowedBooks
import com.example.e_library.ui.theme.screens.borrowing.ViewStaffBorrowedBooks
import com.example.e_library.ui.theme.screens.borrowing.ViewClientsScreen
import com.example.e_library.ui.theme.screens.cart.AddCartScreen
import com.example.e_library.ui.theme.screens.cart.DeliveryDetails
import com.example.e_library.ui.theme.screens.cart.ViewBooksAddedToCart
import com.example.e_library.ui.theme.screens.cart.ViewCartClientsDeliveryPersonnel
import com.example.e_library.ui.theme.screens.cart.ViewCartItemsForClient
import com.example.e_library.ui.theme.screens.clients.ClientHomeScreen
import com.example.e_library.ui.theme.screens.clients.ClientLogInScreen
import com.example.e_library.ui.theme.screens.clients.ClientRegisterScreen
import com.example.e_library.ui.theme.screens.clients.EditClientInfo
import com.example.e_library.ui.theme.screens.clients.ViewAllBooksClient
import com.example.e_library.ui.theme.screens.clients.ViewClientInfo
import com.example.e_library.ui.theme.screens.contact.ContactDeliveryPersonnelAsAdmin
import com.example.e_library.ui.theme.screens.contact.ContactDeliveryPersonnelAsClient
import com.example.e_library.ui.theme.screens.contact.ContactDeliveryPersonnelAsDeliveryPersonnel
import com.example.e_library.ui.theme.screens.contact.ContactDeliveryPersonnelAsStaff
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsAdmin
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsClient
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsDeliveryPersonnel
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsStaff
import com.example.e_library.ui.theme.screens.contact.ContactUsScreen
import com.example.e_library.ui.theme.screens.dashboard.DashboardScreen
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelEditAccount
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelHome
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelLoginScreen
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelRegisterScreen
import com.example.e_library.ui.theme.screens.deliveryPersonnel.DeliveryPersonnelViewAccount
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenAdmin
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenClient
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenDeliveryPersonnel
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenGuest
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenStaff
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenAdmin
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenClient
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenDeliveryPersonnel
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenStaff
import com.example.e_library.ui.theme.screens.home.HomeScreen
import com.example.e_library.ui.theme.screens.home.ViewBooksGuest
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenAdmin
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenClient
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenDeliveryPersonnel
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenGuest
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenStaff
import com.example.e_library.ui.theme.screens.returning.ReturnBooksScreen
import com.example.e_library.ui.theme.screens.splash.SplashScreen
import com.example.e_library.ui.theme.screens.staff.EditStaffInfo
import com.example.e_library.ui.theme.screens.staff.StaffHomeScreen
import com.example.e_library.ui.theme.screens.staff.StaffLogInScreen
import com.example.e_library.ui.theme.screens.staff.StaffRegisterScreen
import com.example.e_library.ui.theme.screens.staff.ViewStaffInfo
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenAdmin
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenClient
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenDeliveryPersonnel
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenGuest
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenStaff

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
               navController: NavHostController = rememberNavController(),
               startDestination: String
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ){
        composable(ROUTE_SPLASH){
            SplashScreen(navController)
        }
        composable(ROUTE_DASHBOARD){
            DashboardScreen(navController)
        }
        composable("$ROUTE_ADMIN_CLIENT_EDIT/{adminId}"){passedData->
            passedData.arguments?.getString("adminId")?.let { AdminClientEdit(navController, it) }
        }
        composable("$ROUTE_ADMIN_EDIT_HOME/{adminId}"){passedData->
            passedData.arguments?.getString("adminId")?.let { AdminEditHome(navController, it) }
        }
        composable(ROUTE_ADMIN_LOGIN){
            AdminLogInScreen(navController)
        }
        composable(ROUTE_ADMIN_REGISTER){
            AdminRegisterScreen(navController)
        }
        composable("$ROUTE_ADMIN_STAFF_EDIT/{adminId}"){passedData->
            passedData.arguments?.getString("adminId")?.let { AdminStaffEdit(navController, it) }
        }
        composable("$ROUTE_ADMIN_DELIVERY_PERSONNEL_EDIT/{adminId}"){passedData->
            passedData.arguments?.getString("adminId")?.let {
                AdminDeliveryPersonnelEdit(navController, it)
            }
        }
        composable("$ROUTE_ADMIN_VIEW_ACCOUNT/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { AdminViewAccount(navController, it) }
        }
        composable("$ROUTE_ADMIN_EDIT_ACCOUNT/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { AdminEditAccount(navController, it) }
        }
        composable("$ROUTE_ADD_BOOKS/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let { AddBooksScreen(navController, it) }
        }
        composable("$ROUTE_BOOKS_HOME/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let { BooksHomeScreen(navController, it) }
        }
        composable("$ROUTE_EDIT_BOOKS/{bookId}/{staffId}"){passedData ->
            EditBooksScreen(
                navController,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("staffId")!!
            )  // need for edit
        }
        composable("$ROUTE_VIEW_BOOKS/{clientId}/{staffId}"){passedData ->
            ViewBooksScreen(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("staffId")!!
            )  // need for edit
        }
        composable("$ROUTE_VIEW_ALL_BOOKS/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let {
                ViewAllBooksScreen(navController, it)
            }  // need for edit
        }
        composable(ROUTE_VIEW_BOOKS_GUEST){
            ViewBooksGuest(navController)  // need for edit
        }
        composable("$ROUTE_VIEW_ALL_BOOKS_CLIENT/{clientId}"){passedData->
            passedData.arguments?.getString("clientId")?.let {
                ViewAllBooksClient(navController, it)
            }  // need for edit
        }
        composable("$ROUTE_BORROW_BOOKS_STAFF/{clientId}/{bookId}/{staffId}"){ passedData ->
            BorrowBooksStaffScreen(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("staffId")!!
            )
        }
        composable("$ROUTE_BORROW_BOOKS_CLIENT/{bookId}/{clientId}"){ passedData ->
            BorrowBooksClientScreen(
                navController,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("clientId")!!,
            )
        }

        composable("$ROUTE_BORROW_HOME/{clientId}"){passedData ->
            passedData.arguments?.getString("clientId")?.let { BorrowHomeScreen(navController, it) }
        }
        composable("$ROUTE_VIEW_CLIENTS/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let {
                ViewClientsScreen(navController, it)
            }  // need for edit
        }
        composable(ROUTE_CLIENT_LOGIN){
            ClientLogInScreen(navController)
        }
        composable(ROUTE_CLIENT_REGISTER){
            ClientRegisterScreen(navController)
        }
        composable(ROUTE_CLIENT_HOME){
            ClientHomeScreen(navController)
        }
        composable("$ROUTE_VIEW_CLIENT_INFO/{clientId}"){passedData->
            passedData.arguments?.getString("clientId")?.let { ViewClientInfo(navController, it) }
        }
        composable("$ROUTE_EDIT_CLIENT_INFO/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")?.let { EditClientInfo(navController, it) }
        }
        composable("$ROUTE_VIEW_STAFF_BORROWS/{clientId}/{staffId}"){passedData ->
            ViewStaffBorrowedBooks(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("staffId")!!
            )
        }
        composable(ROUTE_HOME){
            HomeScreen(navController)
        }
        composable("$ROUTE_RETURN_BOOKS/{clientId}/{bookId}/{staffId}/{borrowId}"){passedData ->
            ReturnBooksScreen(navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("staffId")!!,
                passedData.arguments?.getString("borrowId")!!
            )  //need for edit
        }
        composable("$ROUTE_VIEW_CLIENT_BORROWS/{clientId}"){passedData->
            passedData.arguments?.getString("clientId")?.let {
                ViewClientBorrowedBooks(navController, it)
            }
        }
        composable(ROUTE_STAFF_HOME){
            StaffHomeScreen(navController)
        }
        composable(ROUTE_STAFF_REGISTER){
            StaffRegisterScreen(navController)
        }
        composable(ROUTE_STAFF_LOGIN){
            StaffLogInScreen(navController)
        }
        composable("$ROUTE_VIEW_STAFF_INFO/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let { ViewStaffInfo(navController, it) }
        }
        composable("$ROUTE_EDIT_STAFF_INFO/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")?.let { EditStaffInfo(navController, it) }
        }
        composable("$ROUTE_STAFF_FEEDBACK/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let { FeedbackScreenStaff(navController, it) }
        }
        composable("$ROUTE_CLIENT_FEEDBACK/{clientId}"){passedData->
            passedData.arguments?.getString("clientId")?.let { FeedbackScreenClient(navController, it) }
        }
        composable("$ROUTE_ADMIN_FEEDBACK/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { FeedbackScreenAdmin(navController, it) }
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_FEEDBACK/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let { FeedbackScreenDeliveryPersonnel(navController, it) }
        }
        composable("$ROUTE_ABOUT_SCREEN_STAFF/{staffId}"){passedData->
            passedData.arguments?.getString("staffId")?.let { AboutScreenStaff(navController, it) }
        }
        composable("$ROUTE_ABOUT_SCREEN_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")?.let { AboutScreenClient(navController, it) }
        }
        composable("$ROUTE_ABOUT_SCREEN_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { AboutScreenAdmin(navController, it) }
        }
        composable("$ROUTE_ABOUT_SCREEN_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let { AboutScreenDeliveryPersonnel(navController, it) }
        }
        composable(ROUTE_ABOUT_SCREEN_GUEST){
            AboutScreenGuest(navController)
        }
        composable("$ROUTE_STAFF_CONTACT_AS_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")?.let { ContactStaffAsClient(navController, it) }
        }
        composable("$ROUTE_STAFF_CONTACT_AS_STAFF/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")?.let { ContactStaffAsStaff(navController, it) }
        }
        composable("$ROUTE_STAFF_CONTACT_AS_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { ContactStaffAsAdmin(navController, it) }
        }
        composable("$ROUTE_STAFF_CONTACT_AS_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let { ContactStaffAsDeliveryPersonnel(navController, it) }
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_CONTACT_AS_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")?.let { ContactDeliveryPersonnelAsClient(navController, it) }
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_CONTACT_AS_STAFF/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")?.let { ContactDeliveryPersonnelAsStaff(navController, it) }
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_CONTACT_AS_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")?.let { ContactDeliveryPersonnelAsAdmin(navController, it) }
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_CONTACT_AS_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let { ContactDeliveryPersonnelAsDeliveryPersonnel(navController, it) }
        }
        composable(ROUTE_CONTACT_US){
            ContactUsScreen(navController)
        }
        composable("$ROUTE_EULA_STAFF/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")?.let { EndUserLicenceAgreementScreenStaff(navController, it) }
        }
        composable("$ROUTE_EULA_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")
                ?.let { EndUserLicenceAgreementScreenClient(navController, it) }
        }
        composable("$ROUTE_EULA_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")
                ?.let { EndUserLicenceAgreementScreenAdmin(navController, it) }
        }
        composable("$ROUTE_EULA_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")
                ?.let { EndUserLicenceAgreementScreenDeliveryPersonnel(navController, it) }
        }
        composable(ROUTE_EULA_GUEST){
            EndUserLicenceAgreementScreenGuest(navController)
        }
        composable("$ROUTE_PRIVACY_POLICY_STAFF/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")
                ?.let { PrivacyPolicyScreenStaff(navController, it) }
        }
        composable("$ROUTE_PRIVACY_POLICY_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")
                ?.let { PrivacyPolicyScreenClient(navController, it) }
        }
        composable("$ROUTE_PRIVACY_POLICY_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")
                ?.let { PrivacyPolicyScreenAdmin(navController, it) }
        }
        composable("$ROUTE_PRIVACY_POLICY_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")
                ?.let { PrivacyPolicyScreenDeliveryPersonnel(navController, it) }
        }
        composable(ROUTE_PRIVACY_POLICY_GUEST){
            PrivacyPolicyScreenGuest(navController)
        }
        composable("$ROUTE_USER_MANUAL_STAFF/{staffId}"){ passedData->
            passedData.arguments?.getString("staffId")
                ?.let { UserManualScreenStaff(navController, it) }
        }
        composable("$ROUTE_USER_MANUAL_CLIENT/{clientId}"){ passedData->
            passedData.arguments?.getString("clientId")
                ?.let { UserManualScreenClient(navController, it) }
        }
        composable("$ROUTE_USER_MANUAL_ADMIN/{adminId}"){ passedData->
            passedData.arguments?.getString("adminId")
                ?.let { UserManualScreenAdmin(navController, it) }
        }
        composable("$ROUTE_USER_MANUAL_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")
                ?.let { UserManualScreenDeliveryPersonnel(navController, it) }
        }
        composable(ROUTE_USER_MANUAL_GUEST){
            UserManualScreenGuest(navController)
        }
        composable("$ROUTE_ADD_CART/{clientId}/{bookId}"){passedData->
            AddCartScreen(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!
            )
        }
        composable("$ROUTE_VIEW_CART_CLIENT/{clientId}"){passedData->
            passedData.arguments?.getString("clientId")?.let {
                ViewBooksAddedToCart(
                    navController,
                    it
                )
            }
        }
        composable("$ROUTE_VIEW_CART_CLIENTS_DELIVERY_PERSONNEL/{deliveryPersonnelId}"){ passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let { ViewCartClientsDeliveryPersonnel(navController, it)
            }
        }
        composable("$ROUTE_VIEW_CART_ITEMS_FOR_CLIENT/{deliveryPersonnelId}/{clientId}"){passedData->
            ViewCartItemsForClient(
                navController,
                passedData.arguments?.getString("deliveryPersonnelId")!!,
                passedData.arguments?.getString("clientId")!!
            )
        }
        composable("$ROUTE_DELIVERY_PERSONNEL_HOME/{deliveryPersonnelId}"){passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let {
                DeliveryPersonnelHome(
                    navController,
                    it
                )
            }
        }
        composable(ROUTE_DELIVERY_PERSONNEL_LOGIN){
            DeliveryPersonnelLoginScreen(navController)

        }
        composable(ROUTE_DELIVERY_PERSONNEL_REGISTER){
            DeliveryPersonnelRegisterScreen(navController)
        }
        composable("$ROUTE_VIEW_DELIVERY_PERSONNEL_ACCOUNT/{deliveryPersonnelId}"){passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let {
                DeliveryPersonnelViewAccount(
                    navController,
                    it
                )
            }
        }
        composable("$ROUTE_EDIT_DELIVERY_PERSONNEL_ACCOUNT/{deliveryPersonnelId}"){passedData->
            passedData.arguments?.getString("deliveryPersonnelId")?.let {
                DeliveryPersonnelEditAccount(
                    navController,
                    it
                )
            }
        }
        composable("$ROUTE_DELIVERY_DETAILS/{deliveryPersonnelId}/{cartOrderId}/{clientId}/{bookId}"){passedData->
            DeliveryDetails(
                navController,
                passedData.arguments?.getString("deliveryPersonnelId")!!,
                passedData.arguments?.getString("cartOrderId")!!,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!,
            )
        }
    }
}
