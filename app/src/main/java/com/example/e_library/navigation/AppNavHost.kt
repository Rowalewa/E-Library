package com.example.e_library.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_library.ui.theme.screens.about.AboutScreenAdmin
import com.example.e_library.ui.theme.screens.about.AboutScreenClient
import com.example.e_library.ui.theme.screens.about.AboutScreenGuest
import com.example.e_library.ui.theme.screens.about.AboutScreenStaff
import com.example.e_library.ui.theme.screens.admin.AdminClientEdit
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
import com.example.e_library.ui.theme.screens.borrowing.BorrowBooksScreen
import com.example.e_library.ui.theme.screens.borrowing.BorrowHomeScreen
import com.example.e_library.ui.theme.screens.borrowing.ViewClientsScreen
import com.example.e_library.ui.theme.screens.clients.ClientHomeScreen
import com.example.e_library.ui.theme.screens.clients.ClientLogInScreen
import com.example.e_library.ui.theme.screens.clients.ClientRegisterScreen
import com.example.e_library.ui.theme.screens.clients.EditClientInfo
import com.example.e_library.ui.theme.screens.clients.ViewAllBooksClient
import com.example.e_library.ui.theme.screens.clients.ViewBorrowedBooks
import com.example.e_library.ui.theme.screens.clients.ViewClientInfo
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsAdmin
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsClient
import com.example.e_library.ui.theme.screens.contact.ContactStaffAsStaff
import com.example.e_library.ui.theme.screens.contact.ContactUsScreen
import com.example.e_library.ui.theme.screens.dashboard.DashboardScreen
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenAdmin
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenClient
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenGuest
import com.example.e_library.ui.theme.screens.endUserLicenceAgreement.EndUserLicenceAgreementScreenStaff
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenAdmin
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenClient
import com.example.e_library.ui.theme.screens.feedback.FeedbackScreenStaff
import com.example.e_library.ui.theme.screens.home.HomeScreen
import com.example.e_library.ui.theme.screens.home.ViewBooksGuest
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenAdmin
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenClient
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenGuest
import com.example.e_library.ui.theme.screens.privacyPolicy.PrivacyPolicyScreenStaff
import com.example.e_library.ui.theme.screens.returning.ReturnBooksScreen
import com.example.e_library.ui.theme.screens.returning.ViewClientBorrowedBooks
import com.example.e_library.ui.theme.screens.staff.EditStaffInfo
import com.example.e_library.ui.theme.screens.staff.StaffHomeScreen
import com.example.e_library.ui.theme.screens.staff.StaffLogInScreen
import com.example.e_library.ui.theme.screens.staff.StaffRegisterScreen
import com.example.e_library.ui.theme.screens.staff.ViewStaffInfo
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenAdmin
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenClient
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenGuest
import com.example.e_library.ui.theme.screens.userManual.UserManualScreenStaff

@Composable
fun AppNavHost(modifier: Modifier = Modifier,
               navController: NavHostController = rememberNavController(),
               startDestination: String = ROUTE_DASHBOARD
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ){
        composable(ROUTE_DASHBOARD){
            DashboardScreen(navController)
        }
        composable("$ROUTE_ADMIN_CLIENT_EDIT/{adminId}"){passedData->
            AdminClientEdit(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable("$ROUTE_ADMIN_EDIT_HOME/{adminId}"){passedData->
            AdminEditHome(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_ADMIN_LOGIN){
            AdminLogInScreen(navController)
        }
        composable(ROUTE_ADMIN_REGISTER){
            AdminRegisterScreen(navController)
        }
        composable("$ROUTE_ADMIN_STAFF_EDIT/{adminId}"){passedData->
            AdminStaffEdit(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable("$ROUTE_ADMIN_VIEW_ACCOUNT/{adminId}"){ passedData->
            AdminViewAccount(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable("$ROUTE_ADMIN_EDIT_ACCOUNT/{adminId}"){ passedData->
            AdminEditAccount(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable("$ROUTE_ADD_BOOKS/{staffId}"){passedData->
            AddBooksScreen(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_BOOKS_HOME/{staffId}"){passedData->
            BooksHomeScreen(navController, passedData.arguments?.getString("staffId")!!)
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
            ViewAllBooksScreen(
                navController,
                passedData.arguments?.getString("staffId")!!
            )  // need for edit
        }
        composable(ROUTE_VIEW_BOOKS_GUEST){
            ViewBooksGuest(navController)  // need for edit
        }
        composable("$ROUTE_VIEW_ALL_BOOKS_CLIENT/{clientId}"){passedData->
            ViewAllBooksClient(
                navController,
                passedData.arguments?.getString("clientId")!!
            )  // need for edit
        }
        composable("$ROUTE_BORROW_BOOKS/{clientId}/{bookId}/{staffId}"){ passedData ->
            BorrowBooksScreen(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("staffId")!!
            )
        }

        composable("$ROUTE_BORROW_HOME/{clientId}"){passedData ->
            BorrowHomeScreen(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_VIEW_CLIENTS/{staffId}"){passedData->
            ViewClientsScreen(
                navController,
                passedData.arguments?.getString("staffId")!!
            )  // need for edit
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
            ViewClientInfo(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_EDIT_CLIENT_INFO/{clientId}"){ passedData->
            EditClientInfo(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_VIEW_BORROWED_BOOKS/{clientId}"){passedData ->
            ViewBorrowedBooks(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable(ROUTE_HOME){
            HomeScreen(navController)
        }
        composable("$ROUTE_RETURN_BOOKS/{clientId}/{bookId}/{staffId}"){passedData ->
            ReturnBooksScreen(navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("bookId")!!,
                passedData.arguments?.getString("staffId")!!
            )  //need for edit
        }
        composable("$ROUTE_VIEW_CLIENT_BORROWS/{clientId}/{staffId}"){passedData->
            ViewClientBorrowedBooks(
                navController,
                passedData.arguments?.getString("clientId")!!,
                passedData.arguments?.getString("staffId")!!
            )
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
            ViewStaffInfo(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_EDIT_STAFF_INFO/{staffId}"){ passedData->
            EditStaffInfo(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_STAFF_FEEDBACK/{staffId}"){passedData->
            FeedbackScreenStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_CLIENT_FEEDBACK/{clientId}"){passedData->
            FeedbackScreenClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_ADMIN_FEEDBACK/{adminId}"){ passedData->
            FeedbackScreenAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable("$ROUTE_ABOUT_SCREEN_STAFF/{staffId}"){passedData->
            AboutScreenStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_ABOUT_SCREEN_CLIENT/{clientId}"){ passedData->
            AboutScreenClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_ABOUT_SCREEN_ADMIN/{adminId}"){ passedData->
            AboutScreenAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_ABOUT_SCREEN_GUEST){
            AboutScreenGuest(navController)
        }
        composable("$ROUTE_STAFF_CONTACT_AS_CLIENT/{clientId}"){ passedData->
            ContactStaffAsClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_STAFF_CONTACT_AS_STAFF/{staffId}"){ passedData->
            ContactStaffAsStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_STAFF_CONTACT_AS_ADMIN/{adminId}"){ passedData->
            ContactStaffAsAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_CONTACT_US){
            ContactUsScreen(navController)
        }
        composable("$ROUTE_EULA_STAFF/{staffId}"){ passedData->
            EndUserLicenceAgreementScreenStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_EULA_CLIENT/{clientId}"){ passedData->
            EndUserLicenceAgreementScreenClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_EULA_ADMIN/{adminId}"){ passedData->
            EndUserLicenceAgreementScreenAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_EULA_GUEST){
            EndUserLicenceAgreementScreenGuest(navController)
        }
        composable("$ROUTE_PRIVACY_POLICY_STAFF/{staffId}"){ passedData->
            PrivacyPolicyScreenStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_PRIVACY_POLICY_CLIENT/{clientId}"){ passedData->
            PrivacyPolicyScreenClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_PRIVACY_POLICY_ADMIN/{adminId}"){ passedData->
            PrivacyPolicyScreenAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_PRIVACY_POLICY_GUEST){
            PrivacyPolicyScreenGuest(navController)
        }
        composable("$ROUTE_USER_MANUAL_STAFF/{staffId}"){ passedData->
            UserManualScreenStaff(navController, passedData.arguments?.getString("staffId")!!)
        }
        composable("$ROUTE_USER_MANUAL_CLIENT/{clientId}"){ passedData->
            UserManualScreenClient(navController, passedData.arguments?.getString("clientId")!!)
        }
        composable("$ROUTE_USER_MANUAL_ADMIN/{adminId}"){ passedData->
            UserManualScreenAdmin(navController, passedData.arguments?.getString("adminId")!!)
        }
        composable(ROUTE_USER_MANUAL_GUEST){
            UserManualScreenGuest(navController)
        }
    }
}
