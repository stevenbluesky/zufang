define('components/webuploader/preset/html5only', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/widgets/filednd', 'components/webuploader/widgets/filepaste', 'components/webuploader/widgets/filepicker', 'components/webuploader/widgets/image', 'components/webuploader/widgets/queue', 'components/webuploader/widgets/runtime', 'components/webuploader/widgets/upload', 'components/webuploader/widgets/validator', 'components/webuploader/runtime/html5/blob', 'components/webuploader/runtime/html5/dnd', 'components/webuploader/runtime/html5/filepaste', 'components/webuploader/runtime/html5/filepicker', 'components/webuploader/runtime/html5/imagemeta/exif', 'components/webuploader/runtime/html5/image', 'components/webuploader/runtime/html5/transport'], function(require, exports, module) {

  /**
   * @fileOverview 只有html5实现的文件版本。
   */
  
  
  
  var Base = require('components/webuploader/base');
  require('components/webuploader/widgets/filednd');
  require('components/webuploader/widgets/filepaste');
  require('components/webuploader/widgets/filepicker');
  require('components/webuploader/widgets/image');
  require('components/webuploader/widgets/queue');
  require('components/webuploader/widgets/runtime');
  require('components/webuploader/widgets/upload');
  require('components/webuploader/widgets/validator');
  require('components/webuploader/runtime/html5/blob');
  require('components/webuploader/runtime/html5/dnd');
  require('components/webuploader/runtime/html5/filepaste');
  require('components/webuploader/runtime/html5/filepicker');
  require('components/webuploader/runtime/html5/imagemeta/exif');
  require('components/webuploader/runtime/html5/image');
  require('components/webuploader/runtime/html5/transport');
  module.exports = Base || module.exports;;

});