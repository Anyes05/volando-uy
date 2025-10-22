// errorHandler.js - Sistema global de manejo de errores estético
// Reemplaza alert(), confirm() y otros diálogos del navegador con notificaciones estéticas

(function() {
    'use strict';
    
    // ========== CONFIGURACIÓN ==========
    const CONFIG = {
        toastDuration: 4000,
        animationDuration: 300,
        maxToasts: 3
    };
    
    // ========== FUNCIONES GLOBALES DE NOTIFICACIÓN ==========
    
    /**
     * Muestra una notificación toast estética
     * @param {string} message - Mensaje a mostrar
     * @param {string} type - Tipo de notificación: 'info', 'success', 'warning', 'error'
     * @param {number} duration - Duración en ms (opcional)
     */
    window.showToast = function(message, type = 'info', duration = CONFIG.toastDuration) {
        const toast = createToastElement(message, type);
        document.body.appendChild(toast);
        
        // Animar entrada
        requestAnimationFrame(() => {
            toast.classList.add('show');
        });
        
        // Auto-ocultar
        setTimeout(() => {
            hideToast(toast);
        }, duration);
        
        // Limitar cantidad de toasts
        limitToasts();
    };
    
    /**
     * Muestra un modal de confirmación estético
     * @param {string} message - Mensaje a mostrar
     * @param {string} title - Título del modal (opcional)
     * @param {Array} options - Opciones de botones (opcional)
     * @returns {Promise} - Resuelve con la opción seleccionada
     */
    window.showConfirm = function(message, title = 'Confirmar', options = ['Aceptar', 'Cancelar']) {
        return new Promise((resolve) => {
            const modal = createConfirmModal(message, title, options, resolve);
            document.body.appendChild(modal);
            
            // Animar entrada
            requestAnimationFrame(() => {
                modal.classList.add('show');
            });
        });
    };
    
    /**
     * Muestra un modal de alerta estético
     * @param {string} message - Mensaje a mostrar
     * @param {string} title - Título del modal (opcional)
     * @param {string} type - Tipo de alerta: 'info', 'warning', 'error'
     */
    window.showAlert = function(message, title = 'Información', type = 'info') {
        return new Promise((resolve) => {
            const modal = createAlertModal(message, title, type, resolve);
            document.body.appendChild(modal);
            
            // Animar entrada
            requestAnimationFrame(() => {
                modal.classList.add('show');
            });
        });
    };
    
    /**
     * Muestra un modal de conflicto con opciones (similar a JOptionPane)
     * @param {string} message - Mensaje del conflicto
     * @param {string} title - Título del modal
     * @param {Array} options - Opciones disponibles
     * @returns {Promise} - Resuelve con el índice de la opción seleccionada
     */
    window.showConflictModal = function(message, title = 'Conflicto', options = []) {
        return new Promise((resolve) => {
            const modal = createConflictModal(message, title, options, resolve);
            document.body.appendChild(modal);
            
            // Animar entrada
            requestAnimationFrame(() => {
                modal.classList.add('show');
            });
        });
    };
    
    // ========== FUNCIONES DE CREACIÓN DE ELEMENTOS ==========
    
    function createToastElement(message, type) {
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.setAttribute('role', 'status');
        toast.setAttribute('aria-live', 'polite');
        
        const icon = getIconForType(type);
        toast.innerHTML = `
            <div class="toast-content">
                <i class="fas ${icon}"></i>
                <span class="toast-message">${message}</span>
                <button class="toast-close" onclick="this.parentElement.parentElement.remove()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        `;
        
        return toast;
    }
    
    function createConfirmModal(message, title, options, resolve) {
        const modal = document.createElement('div');
        modal.className = 'modal-overlay confirm-modal';
        
        modal.innerHTML = `
            <div class="modal">
                <div class="modal-header">
                    <h3><i class="fas fa-question-circle"></i> ${title}</h3>
                </div>
                <div class="modal-body">
                    <p>${message}</p>
                </div>
                <div class="modal-footer">
                    ${options.map((option, index) => 
                        `<button class="btn-modal" data-option="${index}">${option}</button>`
                    ).join('')}
                </div>
            </div>
        `;
        
        // Event listeners
        modal.querySelectorAll('.btn-modal').forEach((btn, index) => {
            btn.addEventListener('click', () => {
                hideModal(modal);
                resolve(index);
            });
        });
        
        // Cerrar con ESC
        const handleEscape = (e) => {
            if (e.key === 'Escape') {
                hideModal(modal);
                resolve(-1); // Cancelar
                document.removeEventListener('keydown', handleEscape);
            }
        };
        document.addEventListener('keydown', handleEscape);
        
        return modal;
    }
    
    function createAlertModal(message, title, type, resolve) {
        const modal = document.createElement('div');
        modal.className = 'modal-overlay alert-modal';
        
        const icon = getIconForType(type);
        modal.innerHTML = `
            <div class="modal">
                <div class="modal-header ${type}">
                    <h3><i class="fas ${icon}"></i> ${title}</h3>
                </div>
                <div class="modal-body">
                    <p>${message}</p>
                </div>
                <div class="modal-footer">
                    <button class="btn-modal btn-primary" onclick="this.closest('.modal-overlay').remove()">Aceptar</button>
                </div>
            </div>
        `;
        
        // Auto-resolver cuando se cierre
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                hideModal(modal);
                resolve();
            }
        });
        
        return modal;
    }
    
    function createConflictModal(message, title, options, resolve) {
        const modal = document.createElement('div');
        modal.className = 'modal-overlay conflict-modal';
        
        modal.innerHTML = `
            <div class="modal">
                <div class="modal-header warning">
                    <h3><i class="fas fa-exclamation-triangle"></i> ${title}</h3>
                </div>
                <div class="modal-body">
                    <p>${message}</p>
                    <p>¿Qué deseas hacer?</p>
                </div>
                <div class="modal-footer">
                    ${options.map((option, index) => 
                        `<button class="btn-modal" data-option="${index}">${option}</button>`
                    ).join('')}
                    <button class="btn-modal btn-cancel" data-option="-1">Cancelar</button>
                </div>
            </div>
        `;
        
        // Event listeners
        modal.querySelectorAll('.btn-modal').forEach((btn) => {
            btn.addEventListener('click', (e) => {
                const option = parseInt(e.target.getAttribute('data-option'));
                hideModal(modal);
                resolve(option);
            });
        });
        
        // Cerrar con ESC
        const handleEscape = (e) => {
            if (e.key === 'Escape') {
                hideModal(modal);
                resolve(-1); // Cancelar
                document.removeEventListener('keydown', handleEscape);
            }
        };
        document.addEventListener('keydown', handleEscape);
        
        return modal;
    }
    
    // ========== FUNCIONES AUXILIARES ==========
    
    function getIconForType(type) {
        const icons = {
            'info': 'fa-info-circle',
            'success': 'fa-check-circle',
            'warning': 'fa-exclamation-triangle',
            'error': 'fa-times-circle'
        };
        return icons[type] || icons['info'];
    }
    
    function hideToast(toast) {
        toast.classList.add('hide');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, CONFIG.animationDuration);
    }
    
    function hideModal(modal) {
        modal.classList.add('hide');
        setTimeout(() => {
            if (modal.parentNode) {
                modal.parentNode.removeChild(modal);
            }
        }, CONFIG.animationDuration);
    }
    
    function limitToasts() {
        const toasts = document.querySelectorAll('.toast');
        if (toasts.length > CONFIG.maxToasts) {
            const oldestToast = toasts[0];
            hideToast(oldestToast);
        }
    }
    
    // ========== FUNCIONES DE VALIDACIÓN ==========
    
    /**
     * Muestra validación en tiempo real para un campo
     * @param {HTMLElement} input - Campo de entrada
     * @param {string} message - Mensaje de validación
     * @param {string} type - Tipo: 'success', 'error', 'loading'
     */
    window.showFieldValidation = function(input, message, type) {
        const container = input.closest('.field') || input.parentNode;
        let validationDiv = container.querySelector('.field-validation');
        
        if (!validationDiv) {
            validationDiv = document.createElement('div');
            validationDiv.className = 'field-validation';
            container.appendChild(validationDiv);
        }
        
        validationDiv.innerHTML = `<small class="validation-${type}">${message}</small>`;
        
        // Actualizar clases del input
        input.classList.remove('success', 'error', 'loading');
        if (type !== 'loading') {
            input.classList.add(type);
        }
    };
    
    /**
     * Limpia la validación de un campo
     * @param {HTMLElement} input - Campo de entrada
     */
    window.clearFieldValidation = function(input) {
        const container = input.closest('.field') || input.parentNode;
        const validationDiv = container.querySelector('.field-validation');
        if (validationDiv) {
            validationDiv.innerHTML = '';
        }
        input.classList.remove('success', 'error', 'loading');
    };
    
    // ========== FUNCIONES DE LOADING ==========
    
    /**
     * Muestra estado de carga en un elemento
     * @param {HTMLElement} element - Elemento a mostrar loading
     */
    window.showLoading = function(element) {
        if (element) {
            element.classList.add('loading');
        }
    };
    
    /**
     * Oculta estado de carga de un elemento
     * @param {HTMLElement} element - Elemento a ocultar loading
     */
    window.hideLoading = function(element) {
        if (element) {
            element.classList.remove('loading');
        }
    };
    
    // ========== FUNCIONES DE UTILIDAD ==========
    
    /**
     * Maneja errores de fetch de forma consistente
     * @param {Response} response - Respuesta del fetch
     * @param {string} context - Contexto del error para logging
     * @returns {Promise} - Datos parseados o error
     */
    window.handleFetchError = async function(response, context = 'API') {
        if (!response.ok) {
            let errorMessage = `Error ${response.status}: ${response.statusText}`;
            try {
                const errorData = await response.json();
                errorMessage = errorData.error || errorData.message || errorMessage;
            } catch (e) {
                // Si no se puede parsear JSON, usar mensaje por defecto
            }
            
            console.error(`${context} Error:`, errorMessage);
            showToast(errorMessage, 'error');
            throw new Error(errorMessage);
        }
        
        try {
            return await response.json();
        } catch (e) {
            console.error(`${context} JSON Parse Error:`, e);
            showToast('Error al procesar la respuesta del servidor', 'error');
            throw new Error('Error al procesar la respuesta del servidor');
        }
    };
    
    /**
     * Reemplaza confirm() nativo con versión estética
     */
    window.confirm = function(message) {
        console.warn('Usando confirm() nativo. Considera usar showConfirm() para mejor UX.');
        return showConfirm(message, 'Confirmar', ['Aceptar', 'Cancelar']).then(option => option === 0);
    };
    
    /**
     * Reemplaza alert() nativo con versión estética
     */
    window.alert = function(message) {
        console.warn('Usando alert() nativo. Considera usar showAlert() para mejor UX.');
        return showAlert(message, 'Información', 'info');
    };
    
    console.log('✅ Sistema de manejo de errores estético cargado');
})();
