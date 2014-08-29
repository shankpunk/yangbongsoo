
// FinalProjectColorView.cpp : CFinalProjectColorView 클래스의 구현
//

#include "stdafx.h"
// SHARED_HANDLERS는 미리 보기, 축소판 그림 및 검색 필터 처리기를 구현하는 ATL 프로젝트에서 정의할 수 있으며
// 해당 프로젝트와 문서 코드를 공유하도록 해 줍니다.
#ifndef SHARED_HANDLERS
#include "FinalProjectColor.h"
#endif

#include "FinalProjectColorDoc.h"
#include "FinalProjectColorView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CFinalProjectColorView

IMPLEMENT_DYNCREATE(CFinalProjectColorView, CView)

BEGIN_MESSAGE_MAP(CFinalProjectColorView, CView)
	// 표준 인쇄 명령입니다.
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CFinalProjectColorView::OnFilePrintPreview)
	ON_WM_CONTEXTMENU()
	ON_WM_RBUTTONUP()
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectColorView::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectColorView::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectColorView::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectColorView::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectColorView::OnMedianSub)
	ON_COMMAND(IDM_MEAN_SUB, &CFinalProjectColorView::OnMeanSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectColorView::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectColorView::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectColorView::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectColorView::OnRotation)
END_MESSAGE_MAP()

// CFinalProjectColorView 생성/소멸

CFinalProjectColorView::CFinalProjectColorView()
{
	// TODO: 여기에 생성 코드를 추가합니다.

}

CFinalProjectColorView::~CFinalProjectColorView()
{
}

BOOL CFinalProjectColorView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: CREATESTRUCT cs를 수정하여 여기에서
	//  Window 클래스 또는 스타일을 수정합니다.

	return CView::PreCreateWindow(cs);
}

// CFinalProjectColorView 그리기

void CFinalProjectColorView::OnDraw(CDC* pDC)
{
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: 여기에 원시 데이터에 대한 그리기 코드를 추가합니다.
	int i,j;
	unsigned char R,G,B;
	for (i=0; i<pDoc->m_height; i++) {
		for (j=0; j<pDoc->m_width; j++) {
			R=pDoc->m_InputImageR[i][j];
			G=pDoc->m_InputImageG[i][j];
			B=pDoc->m_InputImageB[i][j];
			pDC->SetPixel(j+5, i+5, RGB(R,G,B));
		}
	}

	for (i=0; i<pDoc->m_Re_height; i++) {
		for (j=0; j<pDoc->m_Re_width; j++) {
			R=pDoc->m_OutputImageR[i][j];
			G=pDoc->m_OutputImageG[i][j];
			B=pDoc->m_OutputImageB[i][j];
			pDC->SetPixel(j+pDoc->m_width+10, i+5, RGB(R,G,B));
		}
	}
}


// CFinalProjectColorView 인쇄


void CFinalProjectColorView::OnFilePrintPreview()
{
#ifndef SHARED_HANDLERS
	AFXPrintPreview(this);
#endif
}

BOOL CFinalProjectColorView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// 기본적인 준비
	return DoPreparePrinting(pInfo);
}

void CFinalProjectColorView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 인쇄하기 전에 추가 초기화 작업을 추가합니다.
}

void CFinalProjectColorView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 인쇄 후 정리 작업을 추가합니다.
}

void CFinalProjectColorView::OnRButtonUp(UINT /* nFlags */, CPoint point)
{
	ClientToScreen(&point);
	OnContextMenu(this, point);
}

void CFinalProjectColorView::OnContextMenu(CWnd* /* pWnd */, CPoint point)
{
#ifndef SHARED_HANDLERS
	theApp.GetContextMenuManager()->ShowPopupMenu(IDR_POPUP_EDIT, point.x, point.y, this, TRUE);
#endif
}


// CFinalProjectColorView 진단

#ifdef _DEBUG
void CFinalProjectColorView::AssertValid() const
{
	CView::AssertValid();
}

void CFinalProjectColorView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CFinalProjectColorDoc* CFinalProjectColorView::GetDocument() const // 디버그되지 않은 버전은 인라인으로 지정됩니다.
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFinalProjectColorDoc)));
	return (CFinalProjectColorDoc*)m_pDocument;
}
#endif //_DEBUG


// CFinalProjectColorView 메시지 처리기


void CFinalProjectColorView::OnZoomIn()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomIn();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnNearest()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnNearest();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnBilinear()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnBilinear();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnZoomOut()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomOut();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMedianSub()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMedianSub();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMeanSub()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMeanSub();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnTranslation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnTranslation();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMirrorHor()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorHor();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMirrorVer()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorVer();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnRotation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnRotation();
	Invalidate(TRUE);
}
