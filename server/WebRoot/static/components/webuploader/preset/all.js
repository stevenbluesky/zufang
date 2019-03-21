define('components/webuploader/preset/all', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/widgets/filednd', 'components/webuploader/widgets/filepaste', 'components/webuploader/widgets/filepicker', 'components/webuploader/widgets/image', 'components/webuploader/widgets/queue', 'components/webuploader/widgets/runtime', 'components/webuploader/widgets/upload', 'components/webuploader/widgets/validator', 'components/webuploader/widgets/md5', 'components/webuploader/runtime/html5/blob', 'components/webuploader/runtime/html5/dnd', 'components/webuploader/runtime/html5/filepaste', 'components/webuploader/runtime/html5/filepicker', 'components/webuploader/runtime/html5/imagemeta/exif', 'components/webuploader/runtime/html5/androidpatch', 'components/webuploader/runtime/html5/image', 'components/webuploader/runtime/html5/transport', 'components/webuploader/runtime/html5/md5', 'components/webuploader/runtime/flash/filepicker', 'components/webuploader/runtime/flash/image', 'components/webuploader/runtime/flash/transport', 'components/webuploader/runtime/flash/blob', 'components/webuploader/runtime/flash/md5'], function(require, exports, module) {

  /**
   * @fileOverview 完全版本。
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
  require('components/webuploader/widgets/md5');
  require('components/webuploader/runtime/html5/blob');
  require('components/webuploader/runtime/html5/dnd');
  require('components/webuploader/runtime/html5/filepaste');
  require('components/webuploader/runtime/html5/filepicker');
  require('components/webuploader/runtime/html5/imagemeta/exif');
  require('components/webuploader/runtime/html5/androidpatch');
  require('components/webuploader/runtime/html5/image');
  require('components/webuploader/runtime/html5/transport');
  require('components/webuploader/runtime/html5/md5');
  require('components/webuploader/runtime/flash/filepicker');
  require('components/webuploader/runtime/flash/image');
  require('components/webuploader/runtime/flash/transport');
  require('components/webuploader/runtime/flash/blob');
  require('components/webuploader/runtime/flash/md5');
  module.exports = Base || module.exports;;

});