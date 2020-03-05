package com.stations.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stations.repository.ApiRepository
import com.stations.data.RequestAccessToken
import com.stations.data.ResponseAccessToken
import com.stations.response.Response
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import java.io.IOException

class ViewModelTest {

    lateinit var viewModel: AuthViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var repository: ApiRepository

    @Mock
    lateinit var accessToken: RequestAccessToken


    @Mock
    lateinit var responseAccessToken: ResponseAccessToken

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun getToken() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            Mockito.`when`(
                repository.getAccessToken(accessToken)
            ).thenReturn(responseAccessToken)
            viewModel.getAccessToken(accessToken)
            assert(viewModel.liveDataAccessToken.value is Response.SUCCESS)
        }
    }

    @Test
    fun getTokenException() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            Mockito.`when`(
                repository.getAccessToken(accessToken)
            ).thenThrow(MockitoException::class.java)
            viewModel.getAccessToken(accessToken)
            assert(viewModel.liveDataAccessToken.value is Response.Failure)
        }
    }

}