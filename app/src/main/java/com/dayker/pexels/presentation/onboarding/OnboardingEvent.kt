package com.dayker.pexels.presentation.onboarding

sealed class OnboardingEvent {

    object OnClickedForward : OnboardingEvent()

    object OnClickedBackward : OnboardingEvent()

    object OnClickedGetStarted : OnboardingEvent()

}