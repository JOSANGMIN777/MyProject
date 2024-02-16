import { defineStore } from 'pinia';

export const useAlertStore = defineStore('alert', {
	state: () => ({
		alerts: [],
	}),
	actions: {
		vAlert(message, type = 'error') {
			this.alerts.push({ message, type });
			setTimeout(() => {
				this.alerts.shift();
			}, 2000);
		},
		vSuccess(message) {
			this.vAlert(message, 'success');
		},
	},
	
});
const rules = ref({
	username: [
	  (value) => {
		if (value) return true;
		return "Username is requred.";
	  },
	  (value) => {
		if (true) return true; // duplicate 검사 필요
		return "Username already taken. Please try another one.";
	  },
	],
	password: [
	  (value) => {
		if (value) return true;
		return "Password is requred.";
	  },
	],
	name: [
	  (value) => {
		if (value) return true;
		return "Name is required.";
	  },
	  (value) => {
		if (value?.length <= 10) return true;
		return "Name must be less than 10 characters.";
	  },
	],
	email: [
	  (value) => {
		var regex = /^[a-z0-9]+@[a-z.-]+\.[a-z]+$/i;
		if (regex.test(value)) return true;
		return "Must be a valid e-mail.";
	  },
	],
  });
