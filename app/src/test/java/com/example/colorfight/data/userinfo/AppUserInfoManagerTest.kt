package com.example.colorfight.data.userinfo

import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfoDTO
import com.example.colorfight.data.userinfo.model.userinfo.UserInfoConverter
import com.example.colorfight.data.userinfo.model.userinfo.UserInfoDTO
import com.example.colorfight.data.userinfo.services.UserInfoService
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AppUserInfoManagerTest {

	@Test
	fun `the success completable object is sent from manager if api returned it`() {
		val successDto = UserInfoDTO(DeviceInfoDTO(null, "", "", "", ""))
		val successObject = UserInfoConverter.convertToUI(successDto)

		val userInfoServiceMock = mock<UserInfoService> {
			on {addUserInfo(successDto)} doReturn Completable.complete()
		}

		val userInfoManager = AppUserInfoManager(userInfoServiceMock)
		val testObserver = userInfoManager.sendUserInfo(successObject).test()
		testObserver.await()
			.assertComplete()

		testObserver.dispose()
	}

	@Test
	fun `the error completable object is sent from manager if api returned it`() {
		val errorDto = UserInfoDTO(DeviceInfoDTO(null, "", "", "", ""))
		val errorObject = UserInfoConverter.convertToUI(errorDto)
		val completableException = Exception()

		val userInfoServiceMock = mock<UserInfoService> {
			on {addUserInfo(errorDto)} doReturn Completable.error(completableException)
		}

		val userInfoManager = AppUserInfoManager(userInfoServiceMock)
		val testObserver = userInfoManager.sendUserInfo(errorObject).test()
		testObserver.await()
			.assertError(completableException)

		testObserver.dispose()
	}



}