/**
This software artefact is largely based on: https://github.com/ciesielskis/AceEditor

original license following:
@license MIT
Copyright (c) 2015 Horacio "LostInBrittany" Gonzalez

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

@element ace-widget
@blurb LostInBrittany's Ace (http://ace.c9.io/) widget
*/

/* globals ace */
/* eslint new-cap: ["error", { "capIsNewExceptions": ["AceWidgetShadowDom"] }] */

import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';

// import ace from 'ace-builds'
import 'ace-builds/src-noconflict/ace.js';
import 'ace-builds/src-noconflict/ext-language_tools.js';

const CDN = 'https://cdn.jsdelivr.net/npm/ace-builds@1.4.8/src-min-noconflict';

// required to import for xtext-ace to be loaded correctly
import { require, define } from "../xtext/require.js"

var editorFocus = function() { 
  var _self = this;
  setTimeout(function() {
      if (!_self.isFocused())
          _self.textInput.focus();
  });
  this.textInput.$focusScroll = "browser"
  this.textInput.focus();
};

class XtextAceWidget extends PolymerElement {
  static get template() {
    return html`
      <style>
        :host {
          display: block;
          width: 100%;
        }
    
        #editor {
          border: 1px solid #e3e3e3;
          border-radius: 4px;
          width: 99%  !important;
          @apply --xtext-ace-widget-editor;
        }
      </style>
      <div id="editor"></div>
    `;
  }

  static get is() { return 'xtext-ace-widget'; }

  static get properties() {
    return {
      theme: {
        type: String,
        value: 'ace/theme/eclipse',
        observer: 'themeChanged',
      },
      mode: {
        type: String,
        notify: true,
        observer: 'modeChanged',
      },
      value: {
        type: String,
        notify: true,
        observer: 'valueChanged',
      },
      readonly: {
        type: Boolean,
        value: false,
        observer: 'readonlyChanged',
      },
      softtabs: {
        type: Boolean,
        value: true,
        observer: 'softtabsChanged',
      },
      wrap: {
        type: Boolean,
        value: false,
        observer: 'wrapChanged',
      },
      fontSize: {
        type: String,
//        value: '14px',
        observer: 'fontSizeChanged',
      },
      tabSize: {
        type: Number,
        value: 4,
        observer: 'tabSizeChanged',
      },
      autoComplete: {
        type: Object,
        notify: true,
      },
      serviceUrl: {
          type: String,
          value: '/xtext-service',
      },
      syntaxDefinition: {
          type: String,
          value: 'none',
      },
      xtextLang: {
            type: String,
            value: 'none',
      },
      enableHighlighting: {
          type: Object,
          notify: true,
      },
      enableOccurrences: {
          type: Object,
          notify: true,
      },
      enableValidation: {
          type: Object,
          notify: true,
      },
      enableHover: {
          type: Object,
          notify: true,
      },
      enableFormatting: {
          type: Object,
          notify: true,
      },
      enableContentAssist: {
        type: Object,
        notify: true,
      },
      minlines: {
        type: Number,
        value: 15,
      },
      maxlines: {
        type: Number,
        value: 30,
      },
      enableLiveAutocompletion: {
        type: Boolean,
        value: false,
      },
      enableSnippets: {
        type: Boolean,
        value: false,
      },
      initialFocus: {
        type: Boolean,
        value: false,
      },
      placeholder: {
        type: String,
        value: '',
      },
      verbose: {
        type: Boolean,
        value: false,
      },
      baseUrl: {
        type: String,
        value: '',
      },
    };
  }

  async connectedCallback() {
    super.connectedCallback();

//    let baseUrl = "/ace-builds/src-min-noconflict/"
//
//    // In non-minified mode, imports are parallelized, and sometimes `ext-language_tools.js` and
//    // `snippets.js` arrive before `ace.js` is ready. I am adding some tests here with dynamic imports 
//    // to fix thaty
//    // console.debug("import ace");
//    if (!ace) {
//      await import(`${baseUrl}ace.js`);
//    }
//    // console.debug("import ace ext-language-tools");
//    if (!ace.require("ace/ext/language_tools")) {
//      await import(`${baseUrl}ext-language_tools.js`);
//    }
    
    // console.debug("[xtext-ace-widget] connectedCallback")
    let div = this.$.editor;
    div.style.width = '100%';
    div.style.height = '100%';

    let config = {
      // baseUrl: `${CDN}`,
      serviceUrl: this.serviceUrl == undefined ? '/xtext-service' : this.serviceUrl,
      xtextLang: this.xtextLang== undefined ? 'none' : this.xtextLang, // 'none', 'mydsl'
      parent: div,
      syntaxDefinition: this.syntaxDefinition == undefined ? 'none' : this.syntaxDefinition, // 'none', 'mydsl'
      // enableCors: true, // in addition cors had to be handled in server component (see ServerLauncher.xtend)
      // dirtyElement: document.getElementsByClassName(tabId),
      loadFromServer: false,
      sendFullText: true,
      // resourceId: 'some.mydsl',
      enableHighlightingService: this.enableHighlighting == undefined ? true : this.enableHighlighting,
      enableOccurrencesService: this.enableOccurrence == undefined ? true : this.enableOccurrence,
      enableValidationService: this.enableValidation == undefined ? true : this.enableValidation,
      enableContentAssistService: this.enableContentAssist == undefined ? true : this.enableContentAssist,
      enableHoverService: this.enableHover == undefined ? true : this.enableHover,
      enableFormattingService: this.enableFormatting == undefined ? true : this.enableFormatting,
      enableGeneratorService: false,
      enableSaveAction: false // don't want the default xtext-save action
    };
    require(["xtext/xtext-ace"], xtext => {

	    ace.config.set('basePath', CDN);
    	ace.config.set('modePath', CDN);
    	ace.config.set('themePath', CDN);
    	ace.config.set('workerPath', CDN);

        this.editor = xtext.createEditor(config); // regular ace would be: this.editor = ace.edit(div);
	    this.editor.focus = editorFocus;

	    this.dispatchEvent(new CustomEvent('editor-ready', { detail: {value: this.editor, oldValue: null}}));
    	// console.debug("[xtext-ace-widget] connectedCallback done, initializing")
    	this.initializeEditor();
    });
  }

  initializeEditor() {
    let self = this;
    let editor = this.editor;

    this.head = document.head;
    this.injectStyle('#ace_editor\\.css');

    editor.setOption('enableLiveAutocompletion', this.enableLiveAutocompletion);

    editor.on('change', this.editorChangeAction.bind(this));
    editor.on('input', this._updatePlaceholder.bind(this));
    setTimeout(this._updatePlaceholder.bind(this), 100);
    this.session = editor.getSession();

    if (this.initialFocus) {
      editor.focus();
    }

    editor.$blockScrolling = Infinity;

    editor.setTheme(this.theme);

    // Forcing a xyzChanged() call, because the initial one din't do anything as editor wasn't created yet
    this.readonlyChanged();
    this.wrapChanged();
    this.tabSizeChanged();
    this.modeChanged();
    this.softtabsChanged();
    this.fontSizeChanged();
    this.valueChanged();
    
    // min and max lines
    editor.setOptions({
        minLines: this.minlines,
        maxLines: this.maxlines,
    });

    if (this.verbose) {
      console.debug('[xtext-ace-widget] After initializing: editor.getSession().getValue()',
          editor.getSession().getValue());
    }
  }

  fontSizeChanged() {
    if (this.editor == undefined) {
      return;
    }
    this.$.editor.style.fontSize = this.fontSize;
  }

  modeChanged() {
    if (!this.editor) return;
    this.editor.getSession().setMode(this.mode);
  }

  softtabsChanged() {
    if (this.editor == undefined) {
      return;
    }
    this.editor.getSession().setUseSoftTabs(this.softtabs);
  }

  themeChanged() {
    if (this.editor == undefined) {
      return;
    }
    this.editor.setTheme(this.theme);
    return;
  }

  valueChanged() {
    // console.debug("[xtext-ace-widget] valueChanged - ",this.value)
    if (this.editor == undefined) {
      return;
    }
    if (this.editorValue != this.value) {
      console.debug("[xtext-ace-widget] value changed")
      this.editorValue = this.value;
      this.editor.clearSelection();
      this.editor.resize();
    }
  }

  readonlyChanged() {
    if (this.editor == undefined) {
      return;
    }
    this.editor.setReadOnly(this.readonly);
    this.editor.setHighlightActiveLine(!this.readonly);
    this.editor.setHighlightGutterLine(!this.readonly);
    this.editor.renderer.$cursorLayer.element.style.opacity = this.readonly ? 0 : 1;
  }

  wrapChanged() {
    if (this.editor == undefined) {
      return;
    }
    this.editor.getSession().setUseWrapMode(this.wrap);
  }

  tabSizeChanged() {
    if (this.editor == undefined) {
      return;
    }
    if (this.tabSize) {
      this.editor.getSession().setTabSize(this.tabSize);
    }
  }

  editorChangeAction() {
    console.debug("[xtext-ace-widget] editorChangeAction", {value: this.editorValue, oldValue: this.value})
    this.dispatchEvent(new CustomEvent('editor-content', {detail: {value: this.editorValue, oldValue: this.value}}));
  }

  get editorValue() {
	if (this.editor == undefined) {
		return '';
	}
    return this.editor.getValue();
  }

  set editorValue(value) {
    if (value === undefined) {
      return;
    }
    this.value = value;
    if (this.editor == undefined) {
    	return;
    }
	this.editor.setValue(value);
	// console.debug("[xtext-ace-widget] set editorValue", this.editorValue)
  }

  focus() {
    this.editor.focus();
  }

  _updatePlaceholder() {
    let shouldShow = !this.editor.session.getValue().length;
    let node = this.editor.renderer.emptyMessageNode;
    if (this.verbose) {
      console.debug('[xtext-ace-widget] _updatePlaceholder', {shouldShow: shouldShow, node: node});
    }
    if (!shouldShow && node) {
        this.editor.renderer.scroller.removeChild(this.editor.renderer.emptyMessageNode);
        this.editor.renderer.emptyMessageNode = null;
    } else if (shouldShow && !node) {
        if (this.verbose) {
          console.debug('[xtext-ace-widget] _updatePlaceholder - shouldShow && !node');
        }
        node = this.editor.renderer.emptyMessageNode = document.createElement('div');
        node.textContent = this.placeholder;
        node.className = 'ace_comment';
        node.style.padding = '0 9px';
        node.style.zIndex = '1';
        node.style.position = 'absolute';
        node.style.color = '#aaa';
        if (this.verbose) {
          console.debug('[xtext-ace-widget] _updatePlaceholder - node', node);
        }
        this.editor.renderer.scroller.appendChild(node);
    }
  }

  /**
   * Injects a style element into xtext-ace-widget's shadow root
   * @param {CSSSelector} selector for an element in the same shadow tree or document as `xtext-ace-widget`
   */
  injectStyle(selector){
    const lightStyle = this.getRootNode().querySelector(selector) || document.querySelector(selector);
    this.shadowRoot.appendChild(lightStyle.cloneNode(true));
  }
}


window.customElements.define(XtextAceWidget.is, XtextAceWidget);
