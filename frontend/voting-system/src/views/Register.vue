<template>
    <div class="register-container">
      <div class="register-card">
        <h1 class="register-title">會員註冊</h1>
        
        <!-- 錯誤訊息 -->
        <div v-if="message" 
             :class="['message', { 'error': isError, 'success': !isError }]">
          {{ message }}
        </div>
  
        <form @submit.prevent="register" class="register-form">
          <div class="form-group">
            <label for="email">電子信箱</label>
            <input 
              id="email" 
              v-model="form.email" 
              type="email" 
              required
              placeholder="請輸入電子信箱"
              maxlength="255"
              :disabled="loading"
            />
          </div>
  
          <div class="form-group">
            <label for="password">密碼</label>
            <input 
              id="password" 
              v-model="form.password" 
              type="password" 
              required
              placeholder="請輸入密碼"
              :disabled="loading"
              minlength="8"
              maxlength="24"
              title="密碼需包含至少 8 個字符，最大 24 個字符"
            />
          </div>
  
          <div class="form-group">
            <label for="username">用戶名稱</label>
            <input 
              id="username" 
              v-model="form.username" 
              type="text" 
              required
              placeholder="請輸入用戶名稱"
              :disabled="loading"
              maxlength="32"
              title="用戶名稱不得超過 32 個字符"
            />
          </div>
  
          <div class="form-group">
            <label for="role">註冊權限</label>
            <select 
              id="role" 
              v-model="form.role"
              :disabled="loading"
            >
              <option value="User">一般用戶</option>
              <option value="Admin">管理員</option>
            </select>
          </div>
  
          <button 
            type="submit" 
            class="register-button"
            :disabled="loading"
          >
            {{ loading ? '註冊中...' : '確認註冊' }}
          </button>
        </form>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive } from 'vue'
  import { authService } from '../api/authService'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  const loading = ref(false)
  const message = ref('')
  const isError = ref(false)
  
  const form = reactive({
    email: '',
    password: '',
    username: '',
    role: 'User'
  })
  
  const register = async () => {
    loading.value = true
    message.value = ''
    isError.value = false
    
    try {  
      await authService.registerUser(
        form.email,
        form.password,
        form.username,
        form.role
      )
      
      message.value = '註冊成功！即將導向登入頁面...'
      isError.value = false
      
      setTimeout(() => {
        router.push('/login')
      }, 3000)
      
    } catch (error: any) {
      isError.value = true
      if (error.response?.data) {
        message.value = error.response.data
      } else {
        message.value = '註冊失敗，請稍後再試'
      }
    } finally {
      loading.value = false
    }
  }
  </script>
  
  <style scoped>
  .register-container {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    background-color: #f5f5f5;
  }
  
  .register-card {
    background: white;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
  }
  
  .register-title {
    text-align: center;
    color: #333;
    margin-bottom: 1.5rem;
    font-size: 1.5rem;
  }
  
  .register-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .form-group label {
    color: #666;
    font-size: 0.9rem;
  }
  
  .form-group input,
  .form-group select {
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
    transition: border-color 0.2s;
  }
  
  .form-group input:focus,
  .form-group select:focus {
    border-color: #4CAF50;
    outline: none;
  }
  
  .register-button {
    margin-top: 1rem;
    padding: 0.75rem;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.2s;
  }
  
  .register-button:hover:not(:disabled) {
    background-color: #45a049;
  }
  
  .register-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .message {
    padding: 1rem;
    margin-bottom: 1rem;
    border-radius: 4px;
    text-align: center;
  }
  
  .error {
    background-color: #ffebee;
    color: #c62828;
    border: 1px solid #ffcdd2;
  }
  
  .success {
    background-color: #e8f5e9;
    color: #2e7d32;
    border: 1px solid #c8e6c9;
  }
  
  @media (max-width: 480px) {
    .register-card {
      padding: 1.5rem;
    }
  }
  </style>