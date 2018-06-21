import {
  getCustomFetchConfigUsing,
  createPromiseFor,
} from './utils';

/**
 * @method registerUser
 * @description Register a new user
 * @param {Object} params - User params
 * @returns {Promise} Register result
 */
const registerUser = (params) => {
  const path = '/registerUser';
  const options = getCustomFetchConfigUsing(params);
  return createPromiseFor({ path, params: options });
};

/**
 * @method signIn
 * @description Sign in to site
 * @param {Object} params - User params
 * @returns {Promise} Register result
 */
const signIn = (params) => {
  const path = '/signIn';
  const options = getCustomFetchConfigUsing(params);
  return createPromiseFor({ path, params: options });
};


module.exports = {
  registerUser,
  signIn,
};