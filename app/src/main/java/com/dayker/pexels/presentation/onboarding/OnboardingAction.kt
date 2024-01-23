package com.dayker.pexels.presentation.onboarding

sealed class OnboardingAction {

    object ScrollForward : OnboardingAction()

    object ScrollBackward : OnboardingAction()

    object GetStarted : OnboardingAction()

}